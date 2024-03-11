public class User {

    // instance variables
    private String username;
    private String password;
    private int isFirstPurchase;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isFirstPurchase = getIsFirstPurchase();
    }

    public User() {

    }


    // getters
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getIsFirstPurchase() {
        return isFirstPurchase;
    }

    // setters
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setIsFirstPurchase(int isFirstPurchase) {
        this.isFirstPurchase = isFirstPurchase;
    }
}
