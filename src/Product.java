public abstract class Product {

    // instance variables
    private String productID;
    private String productName;
    private int noAvailableItems;
    private double price;
    private String productType;

    // constructor
    public Product(String productID, String productName, double price, String productType) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.productType = productType;
    }

    public Product() {
        this.noAvailableItems = 0;
    }

    // getters
    public String getProductID() {
        return productID;
    }
    public String getProductName() {
        return productName;
    }
    public int getNoAvailableItems() {
        return noAvailableItems;
    }
    public double getPrice() {
        return price;
    }
    public String getProductType(){
        return productType;
    }


    // setters
    public void setProductID(String productID) {
        this.productID = productID;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setNoAvailableItems(int noAvailableItems) {
        this.noAvailableItems = noAvailableItems;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}