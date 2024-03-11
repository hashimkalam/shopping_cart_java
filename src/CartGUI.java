import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;

public class CartGUI {
    static int totalElectronicNo;
    static int totalClothingNo;

    static void shoppingCartFrame(List<Product> updateCartProducts) {
        JFrame shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setLayout(new GridLayout(2, 1));

        String[] columnNames = {"Product", "Quantity", "Price"};
        DefaultTableModel cartTableModel = new DefaultTableModel(columnNames, 0);
        JTable cartTable = new JTable(cartTableModel);

        double totalPrice = 0.0;
        totalElectronicNo = 0;
        totalClothingNo = 0;

        // mapping each product from the updated cart product list
        for (Product product : updateCartProducts) {
            String productDetails;
            int quantity = 1;
            double price;

            productDetails = product.getProductID() + ", " + product.getProductName() + ", ";

            // assigning details to each requirement like product details.
            if (product instanceof Electronics electronicsProduct) {
                productDetails = productDetails + electronicsProduct.getBrand() + ", " +
                        electronicsProduct.getWarrantyPeriod() + " - " + electronicsProduct.getWarrantyTimeSelection();
                totalElectronicNo++;
            } else if (product instanceof Clothing clothingProduct) {
                productDetails = productDetails + clothingProduct.getSize().toUpperCase() + ", " + clothingProduct.getColor();
                totalClothingNo++;
            } else continue;

            // price for each product and total price
            price = product.getPrice();
            totalPrice += product.getPrice();

            // Check if the product is already in the table
            boolean doesProductExist = false;
            for (int row = 0; row < cartTableModel.getRowCount(); row++) {

                // check existent of product with the id
                if (((String) cartTableModel.getValueAt(row, 0)).contains(product.getProductID())) {
                    doesProductExist = true;

                    // getting the row, and updating the quantity
                    int existingQuantity = (int) cartTableModel.getValueAt(row, 1);
                    cartTableModel.setValueAt(existingQuantity + 1, row, 1);
                    break;
                }
            }

            // If the product is not in the table, add a new row
            if (!doesProductExist) {
                Object[] rowData = {productDetails, quantity, price};
                cartTableModel.addRow(rowData);
            }
        }

        // allow the scroll ability to be applied if a lot of products are passed
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        shoppingCartFrame.add(scrollPane);

        // Purchase method
        purchase(shoppingCartFrame, updateCartProducts, totalPrice);

        shoppingCartFrame.setSize(700, 475);
        shoppingCartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shoppingCartFrame.setVisible(true);
    }

    private static void purchase(JFrame shoppingCartFrame, List<Product> updateCartProducts, double totalPrice) {
        // total price
        JLabel totalCart = new JLabel("Total: " + totalPrice);

        // first purchase discount logic
        double firstPurchaseDiscount = 0.0;
        int isFirstPurchaseRetrieved = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader("firstPurchase"))) {
            String line;
            // checking according to username & password whether first purchase is done
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (Register.usernameArea.getText().equals(parts[0]) && Register.passwordField.getText().equals(parts[1])) {
                    isFirstPurchaseRetrieved = Integer.parseInt(parts[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (isFirstPurchaseRetrieved == 0) {
            firstPurchaseDiscount = totalPrice * .1;
        }
        JLabel firstPurchase = new JLabel("First Purchase Discount(10%): -" + firstPurchaseDiscount);

        if (firstPurchaseDiscount > 0) {
            firstPurchase.setForeground(Color.BLUE);
        } else firstPurchase.setForeground(Color.RED);


        JLabel sameCategoryDiscount;

        // three same items discount logic
        double threeItemDiscount = 0.0;
        if (totalElectronicNo > 2 || totalClothingNo > 2) {
            threeItemDiscount = totalPrice * .2;

        }
        sameCategoryDiscount = new JLabel("Three items in same Category Discount(20%): -" + threeItemDiscount);
        if (threeItemDiscount > 0) {
            sameCategoryDiscount.setForeground(Color.BLUE);
        } else sameCategoryDiscount.setForeground(Color.RED);

        // final total price
        double finalPrice = totalPrice - firstPurchaseDiscount - threeItemDiscount;
        JLabel finalTotalCart = new JLabel("Final Total: " + finalPrice);

        JPanel bottomCart = new JPanel();
        bottomCart.setBackground(Color.pink);
        bottomCart.setLayout(new GridLayout(6, 1));
        bottomCart.setBorder(new EmptyBorder(40, 40, 40, 40));

        bottomCart.add(totalCart);
        bottomCart.add(firstPurchase);
        bottomCart.add(sameCategoryDiscount);
        bottomCart.add(finalTotalCart);

        purchaseClick(finalPrice, shoppingCartFrame, updateCartProducts, bottomCart);

        shoppingCartFrame.add(bottomCart);
    }

    private static void purchaseClick(double finalPrice, JFrame shoppingCartFrame, List<Product> updateCartProducts, JPanel bottomCart) {
        JLabel emptyBasket = new JLabel();
        bottomCart.add(emptyBasket);

        JButton purchase = new JButton("Purchase");
        bottomCart.add(purchase);

        purchase.addActionListener(e -> {
            if (finalPrice > 0) {
                JFrame frame = new JFrame();
                JPanel panel = new JPanel();

                JLabel successMessage = new JLabel("Success!");
                JButton returnBtn = new JButton("Done!");

                returnBtn.addActionListener(event -> {
                    frame.dispose();
                    shoppingCartFrame.dispose();
                });
                panel.add(successMessage);
                panel.add(returnBtn);

                frame.setLayout(new BorderLayout());
                frame.add(panel, BorderLayout.CENTER);
                frame.setSize(250, 75);
                frame.setVisible(true);

                // first purchase has done
                User user = new User();
                user.setIsFirstPurchase(1);

                try (FileWriter write = new FileWriter("firstPurchase", true)) {
                    write.write((Register.usernameArea.getText() + " - " + Register.passwordField.getText() + " - " +
                            user.getIsFirstPurchase() + "\n"));
                } catch (IOException event) {
                    event.printStackTrace();
                }
                // clear products from table
                updateCartProducts.clear();

                totalElectronicNo = 0;
                totalClothingNo = 0;
            } else {
                emptyBasket.setText("Empty Basket!");
                emptyBasket.setForeground(Color.RED);
                emptyBasket.setFont(new Font("SansSerif", Font.BOLD, 16));

            }
        });
    }
}
