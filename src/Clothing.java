public class Clothing extends Product {

    // instance variable
    private String size;
    private String color;

    // constructor
    public Clothing (String productID, String productName, int noAvailableItems, double price, String size, String color) {
        super(productID, productName, price, "Clothing");
        this.setNoAvailableItems(noAvailableItems);
        this.size = size;
        this.color = color;
    }

    // empty constructor
    public Clothing() {

    }


    // getters
    public String getSize() {
        return size;
    }
    public String getColor() {
        return color;
    }

    // setters
    public void setSize(String size) {
        this.size = size;
    }
    public void setColor(String color) {
        this.color = color;
    }
}
