import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GUISection {
    static JLabel productId; static JLabel category; static JLabel name; static JLabel size; static JLabel color;
    static JLabel itemNo; static JLabel brand; static JLabel warranty; static JButton shopCartBtn;
    static  JPanel bottomInnerPanel; static final JLabel bottomSelectionText = new JLabel();
    private List<Product> updateCartProducts = new ArrayList<>();
    static Product selectedProduct; static JTable table;

    // empty constructor
    public GUISection() {}

    // getter for update cart products list
    private List<Product> getUpdateCartProducts() {
        return updateCartProducts;
    }

    // setter for updating the cart list of the cart gui
    private void setUpdateCartProducts(List<Product> selectedProductList) {
        this.updateCartProducts = selectedProductList;
    }

    static GUISection guiSection = new GUISection();
    public static void shoppingManagerGui(List<Product> productList) {

        JFrame managerFrame = new JFrame("Westminster Shopping Centre");
        JPanel panel = new JPanel();

        JLabel selectionText = new JLabel("Select Product Category");
        JButton cartButton = new JButton("Shopping Cart");
        JButton sortButton = new JButton("Sort");
        JButton logoutButton = new JButton("Logout");

        String[] productTypes = {"All", "Electronic", "Clothes"};
        JComboBox<String> productDropDown = new JComboBox<>(productTypes);

        panel.setLayout(new GridLayout(2, 1));

        JPanel topInnerPanel = new JPanel();
        topInnerPanel.setLayout(new FlowLayout());
        topInnerPanel.add(selectionText);
        topInnerPanel.add(productDropDown);

        // open the shopping cart frame when clicked on the cart button
        cartButton.addActionListener(e -> CartGUI.shoppingCartFrame(guiSection.getUpdateCartProducts()));

        // logout
        logoutButton.addActionListener(e -> {
            managerFrame.dispose();
            Register.register(productList);
            guiSection.getUpdateCartProducts().clear();
        });

        topInnerPanel.add(sortButton);
        topInnerPanel.add(cartButton);
        topInnerPanel.add(logoutButton);

        topInnerPanel.setBackground(new Color(204, 229, 240));

        String[] columnNames = {"Product ID", "Name", "Category", "Price", "Info"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        table = new JTable(tableModel);
        productTable(productList, tableModel, table);

        table.revalidate();

        // filtering the products according to the type selected
        productDropDown.addActionListener(e -> filterTable(productDropDown, tableModel, productList, table));

        // sorting the products
        sortButton.addActionListener(e -> {
            productList.sort(Comparator.comparing(Product::getProductName));

            tableModel.setRowCount(0); // clearing the table

            productTable(productList, tableModel, table); // mapping the sorted values

            // refreshing
            table.revalidate();
            table.repaint();
        });

        // adding listener to show the product when selected
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            updateProductDetails(selectedRow, tableModel, productList, table);
        });

        // to enable scrolling if many product are available
        JScrollPane scrollPane = new JScrollPane(table);

        topInnerPanel.add(scrollPane);
        panel.add(topInnerPanel);

        productId = new JLabel(); category = new JLabel(); name = new JLabel();
        size = new JLabel(); color = new JLabel(); itemNo = new JLabel();
        brand = new JLabel(); warranty = new JLabel();
        bottomInnerPanel = new JPanel();
        shopCartBtn = new JButton();

        bottomInnerPanel.setLayout(new GridLayout(8, 1));

        // adding padding to the bottom container
        bottomInnerPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        bottomInnerPanel.setBackground(new Color(180, 180, 255));
        bottomSelectionText.setText("Select Product - Details");
        bottomInnerPanel.add(bottomSelectionText);

        // adding empty tags so that the shop button goes to the rock bottom
        bottomInnerPanel.add(productId); bottomInnerPanel.add(category);
        bottomInnerPanel.add(name); bottomInnerPanel.add(size);
        bottomInnerPanel.add(itemNo); bottomInnerPanel.add(brand);

        // shop cart button
        shopCartBtn = new JButton("Add To Shopping Cart");
        bottomInnerPanel.add(shopCartBtn);

        panel.add(bottomInnerPanel);

        managerFrame.setLayout(new BorderLayout());
        managerFrame.add(panel, BorderLayout.CENTER);
        managerFrame.setSize(850, 700);
        managerFrame.setVisible(true);
    }


    // method to filter the table according to the product type chosen
    private static void filterTable(JComboBox<String> productDropDown, DefaultTableModel tableModel, List<Product> productList, JTable table) {
        String selectedCategory = (String) productDropDown.getSelectedItem();
        tableModel.setRowCount(0);

        if ("All".equals(selectedCategory)) {
            productTable(productList, tableModel, table);
        }

        for(Product product : productList) {
            if ("Electronic".equals(selectedCategory) && product instanceof Electronics electronicsProduct) {
                electronicsTable(tableModel, electronicsProduct);
            } else if ("Clothes".equals(selectedCategory) && product instanceof Clothing clothingProduct) {
                clothingTable(tableModel, clothingProduct);
            }
        }
    }


    // method to set the electronic part of the table
    private static void electronicsTable(DefaultTableModel tableModel, Electronics electronicsProduct) {
        if (electronicsProduct.getNoAvailableItems() > 0) {
            Object[] rowData = {
                    electronicsProduct.getProductID(), electronicsProduct.getProductName(), electronicsProduct.getProductType(),
                    electronicsProduct.getPrice(), electronicsProduct.getBrand() + ", " + electronicsProduct.getWarrantyPeriod()
                    + electronicsProduct.getWarrantyTimeSelection() + " warranty"
            };
            tableModel.addRow(rowData);
        }
    }


    // method to set the clothing part of the table
    private static void clothingTable( DefaultTableModel tableModel, Clothing clothingProduct) {
        if (clothingProduct.getNoAvailableItems() > 0) {
            Object[] rowData = {
                    clothingProduct.getProductID(), clothingProduct.getProductName(), clothingProduct.getProductType(),
                    clothingProduct.getPrice(), clothingProduct.getSize().toUpperCase() + ", " + clothingProduct.getColor()
            };
            tableModel.addRow(rowData);
        }
    }


    // method to set the product table
    static void productTable(List<Product> productList, DefaultTableModel tableModel, JTable table) {
        for (Product product : productList) {
            if (product instanceof Electronics electronicsProduct) {
                electronicsTable(tableModel, electronicsProduct);

            } else if (product instanceof Clothing clothingProduct) {
                clothingTable(tableModel, clothingProduct);
            }
        }
        changeRowColor(productList, table);
    }



    private static class CellRed extends DefaultTableCellRenderer {
        private final ArrayList<Integer> rowIndexList;
        public CellRed(ArrayList<Integer> rowIndex) {
            this.rowIndexList = rowIndex;
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellTriggered = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (rowIndexList.contains(row)) {
                cellTriggered.setBackground(Color.red);
            } else cellTriggered.setBackground(table.getBackground());

            return cellTriggered;
        }
    }


    // method to display the product details when selected at the bottom
    private static void updateProductDetails(int selectedRow, DefaultTableModel tableModel, List<Product> productList, JTable table) {
        if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
            productId.setText("Product ID: " + tableModel.getValueAt(selectedRow, 0));
            name.setText("Name: " + tableModel.getValueAt(selectedRow, 1));
            category.setText("Category: " + tableModel.getValueAt(selectedRow, 2));

            bottomInnerPanel.removeAll();

            bottomInnerPanel.add(bottomSelectionText);
            bottomInnerPanel.add(productId);
            bottomInnerPanel.add(category);
            bottomInnerPanel.add(name);

            if  ((productList).get(selectedRow) instanceof Electronics)  {
                brand.setText("Brand: " + ((Electronics) productList.get(selectedRow)).getBrand());
                warranty.setText("Warranty: " + ((Electronics) productList.get(selectedRow)).getWarrantyTimeSelection()
                        + ((Electronics) productList.get(selectedRow)).getWarrantyPeriod());
                bottomInnerPanel.add(brand);
                bottomInnerPanel.add(warranty);

            } else if ((productList).get(selectedRow) instanceof Clothing) {
                size.setText("Size: " + ((Clothing) productList.get(selectedRow)).getSize().toUpperCase());
                color.setText("Colour: " + ((Clothing) productList.get(selectedRow)).getColor());
                bottomInnerPanel.add(size);
                bottomInnerPanel.add(color);
            }
            itemNo.setText("Items Available: " + (productList.get(selectedRow)).getNoAvailableItems());
            bottomInnerPanel.add(itemNo);

            addToCart(selectedRow, tableModel, table, productList); // call method to add cart

            tableModel.setRowCount(0);
            productTable(productList, tableModel, table);
            table.revalidate();
            table.repaint();

            bottomInnerPanel.add(shopCartBtn);
            bottomInnerPanel.revalidate();
            bottomInnerPanel.repaint();
        }
    }

    static List<Product> selectedProductList = new ArrayList<>();
    // method to add the selected products to the cart
    private static void addToCart(int selectedRow, DefaultTableModel tableModel, JTable table, List<Product> productList) {
        shopCartBtn = new JButton("Add To Shopping Cart");

        // function to add products to shopping cart
        shopCartBtn.addActionListener(e -> {
            if (selectedRow < tableModel.getRowCount()) {
                selectedProduct = productList.get(selectedRow);

                // add the selected products to the array
                selectedProductList.add(selectedProduct);

                // pass the array to the setter update cart product
                guiSection.setUpdateCartProducts(selectedProductList);
                updateProductItems(productList, selectedProduct, table);

                // CartGUI.shoppingCartFrame(guiSection.getUpdateCartProducts()); // open shopping cart frame when clicked on cart button

                if (selectedProduct.getNoAvailableItems() < 1) {

                    tableModel.setRowCount(0); // clearing the table

                    productTable(productList, tableModel, table); // mapping the sorted values

                    table.revalidate();
                    table.repaint();
                }
            }
        });
    }

    // method to update the product item quantity
    private static void updateProductItems(List<Product> productList, Product selectedProduct, JTable table) {
        for (Product product : productList) {
            if (product.getProductID().equals(selectedProduct.getProductID())) {
                int updatedAvailableItems = product.getNoAvailableItems() - 1;
                product.setNoAvailableItems(updatedAvailableItems);

                // remove the index of the product when quantity is less than 1, to ensure that the next product maintain its quantity
                if (updatedAvailableItems < 1) {
                    productList.remove(product);
                }
                break;
            }
        } changeRowColor(productList, table);
    }

    private static void changeRowColor(List<Product> productList, JTable table) {
        ArrayList<Integer> indexList = new ArrayList<>();
        for (Product product : productList) {
            if(product.getNoAvailableItems() <= 3) {
                int rowIndex = productList.indexOf(product);
                indexList.add(rowIndex);
            }
        }

        table.setDefaultRenderer(Object.class, new CellRed(indexList));

        table.revalidate();
        table.repaint();
    }
}