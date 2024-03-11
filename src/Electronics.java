public class Electronics extends Product {
    // instance variable
    private String brand;
    private int warrantyPeriod;
    private String warrantyTimeSelection;

    // constructor

    public Electronics(String productID, String productName, int noAvailableItems, double price, String brand, int warrantyPeriod, String warrantyTimeSelection) {
        super(productID, productName, price, "Electronic");
        this.setNoAvailableItems(noAvailableItems);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
        this.warrantyTimeSelection = warrantyTimeSelection;
    }

    // empty constructor
    public Electronics() {

    }



    // getters
    public String getBrand() {
        return brand;
    }
    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }
    public String getWarrantyTimeSelection() {
        return warrantyTimeSelection;
    }

    // setters
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
    public void setWarrantyTimeSelection(String warrantyTimeSelection) {
        this.warrantyTimeSelection = warrantyTimeSelection;
    }


    public String toString() {
        return "[ID]-" + getProductID() + " | [Name]-" + getProductName() + " | [Type]-" + getProductType() +
                " | [Price]-$" + getPrice() + " | [Brand]-" + getBrand() + " | [Warranty Period]" + getWarrantyTimeSelection()
                +  "- " + getWarrantyPeriod() + " | [no available items]- " + getNoAvailableItems();
    }





}
