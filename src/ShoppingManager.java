import java.io.IOException;

public interface ShoppingManager {
    void displayMenu();
    void addProduct();
    void removeProduct();
    void displayProducts();
    void totalCostCalc();
    void clearList();
    void saveToFile() throws IOException;
    void retrieveData() throws IOException;
}
