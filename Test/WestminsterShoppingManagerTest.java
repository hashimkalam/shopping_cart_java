import java.io.*;
import java.util.List;

import static org.junit.Assert.*;
public class WestminsterShoppingManagerTest {

    private WestminsterShoppingManager manager;
    private ShoppingCart cart;
    private Clothing clothing;
    private Electronics electronics;
    private ByteArrayOutputStream outputStream;
    @org.junit.Before
    public void setUp() {
        manager = new WestminsterShoppingManager();
        cart = new ShoppingCart();
        clothing = new Clothing("ew14", "Shirt", 14, 58, "S", "Black");
        electronics = new Electronics("da24", "Laptop", 24, 2150,"Dell",
                24, "Weeks");

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @org.junit.Test
    public void testForAddElectronicProduct() {
        List<Product> productList = cart.getProductsList();

        cart.addProduct(electronics);

        assertEquals("da24", productList.get(0).getProductID());
        assertEquals("Laptop", productList.get(0).getProductName());
        assertEquals(24, productList.get(0).getNoAvailableItems());
        assertEquals(2150, productList.get(0).getPrice(), 0.01);
        assertEquals("Electronic", productList.get(0).getProductType());
        assertEquals("Dell", ((Electronics) productList.get(0)).getBrand());
        assertEquals(24, ((Electronics) productList.get(0)).getWarrantyPeriod());
        assertEquals("Weeks", ((Electronics) productList.get(0)).getWarrantyTimeSelection());
    }

    @org.junit.Test
    public void testForAddClothingProduct() {
        cart.addProduct(clothing);

        List<Product> productList = cart.getProductsList();

        assertEquals("ew14", productList.get(0).getProductID());
        assertEquals("Shirt", productList.get(0).getProductName());
        assertEquals(14, productList.get(0).getNoAvailableItems());
        assertEquals(58, productList.get(0).getPrice(), 0.01); // delta value since its a double value
        assertEquals("Clothing", productList.get(0).getProductType());
        assertEquals("Black", ((Clothing) productList.get(0)).getColor());
        assertEquals("S", ((Clothing) productList.get(0)).getSize());
    }

    @org.junit.Test
    public void testForDeleteProduct(){
        cart.addProduct(electronics);
        cart.addProduct(clothing);

        cart.removeProduct(electronics);

        List<Product> productList = cart.getProductsList();

        // check whether product list contains removed product = false, it passes, so product have removed successfully
        assertFalse(productList.contains(electronics));

        System.out.println("Test Case for Delete Product Pass");
        // assertEquals(productList.remove(electronics), productList);
    }

    @org.junit.Test
    public void testForDisplayProducts() {
        manager.cart.addProduct(electronics);
        manager.displayProducts();

        String printedContent = outputStream.toString().trim();
        assertEquals("1. [ID]-da24 | [Name]-Laptop | [Type]-Electronic | [Price]-$2150.0 | [Brand]-Dell | " +
                "[Warranty Period]Weeks- 24 | [no available items]- 24", printedContent);
    }

    // method to test for saving data
    @org.junit.Test
    public void testForSavingData() throws IOException {
        manager.saveToFile();

        String printedContent = outputStream.toString().trim();
        assertEquals("The data has been successfully saved in the file named data", printedContent);
    }

    // method to test for retrieving data
    @org.junit.Test
    public void testForRetrievingData() throws IOException {
        manager.retrieveData();

        String printedContent = outputStream.toString().trim();
        assertEquals("Retrieved Successfully", printedContent);
    }

    // method to test for total cost calculation
    @org.junit.Test
    public void testForTotalCostCalc() {
        manager.cart.addProduct(electronics);
        manager.totalCostCalc();

        String printedContent = outputStream.toString().trim();
        assertEquals("Total Cost is $51600.0", printedContent);
    }

    // method to test for clearing the list
    @org.junit.Test
    public void testForClearList() {
        manager.cart.addProduct(electronics);
        manager.cart.clearList();

        String printedContent = outputStream.toString().trim();

        // empty list so empty string is assigned as expected
        assertEquals("", printedContent);
    }
}