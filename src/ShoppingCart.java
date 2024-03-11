import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    // instance variable
    private final List<Product> productsList;

    public ShoppingCart() {
        this.productsList = new ArrayList<>();
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    // method for adding products to cart
    public void addProduct(Product product) {
        productsList.add(product);
    }

    // method for removing products from cart
    public void removeProduct(Product product) {
        productsList.remove(product);
    }

    public void clearList(){
        productsList.clear();
    }

    // method to calculate total cost of the products
    public double totalCostCalc() {
        double totalCost = 0;

        for (Product product : productsList) {
            for(int i=0; product.getNoAvailableItems()>i; i++) {
                totalCost += product.getPrice();
            }
        }
        return totalCost;
    }
}
