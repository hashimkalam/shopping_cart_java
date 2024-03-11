import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    ShoppingCart cart = new ShoppingCart();
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        Scanner scanner = new Scanner(System.in);
        shoppingManager.retrieveData();
        int option=0;

        do {
            shoppingManager.displayMenu();
            System.out.print("Enter your option: ");

            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> shoppingManager.addProduct();
                    case 2 -> shoppingManager.removeProduct();
                    case 3 -> shoppingManager.displayProducts();
                    case 4 -> shoppingManager.saveToFile();
                    case 5 -> shoppingManager.totalCostCalc();
                    case 6 -> shoppingManager.clearList();
                    case 7 -> Register.register(shoppingManager.cart.getProductsList());
                    case 0 -> System.out.println("Thank you for using our service. \n *Exited* ");
                    default -> System.out.println("Invalid option entered. Retry by entering a number between 1 to 7!");
                }
            } else {
                System.out.println("Invalid Input data type. Enter an integer!\n");
                scanner.nextLine();
            }
        } while (option != 0);
    }


    // overriding the displayMenu
    @Override
    public void displayMenu() {
        System.out.println("\n----------------MENU-----------------");
        System.out.println("Westminster Shopping Manager Menu");
        System.out.println("1. Add a New Product");
        System.out.println("2. Delete a Product");
        System.out.println("3. Print the list of the Products");
        System.out.println("4. Save in a file");
        System.out.println("5. Calculate the Total Cost");
        System.out.println("6. Clear entire product list");
        System.out.println("7. Open GUI");
        System.out.println("0. Exit");
        System.out.println("-------------------------------------\n");
    }


    // overriding the addProduct to set the method
    @Override
    public void addProduct() {
        if (cart.getProductsList().size() >= 50) {
            System.out.println("Sorry you have reached maximum product storage capacity!\n");
        } else {

            // validation check
            boolean validProductChoice = false;
            int choiceOfProduct = 0;

            while (!validProductChoice) {
                System.out.print("Electronic (type 1) / Clothing (type 2) / go Back (type 0): ");
                if (scanner.hasNextInt()) {
                    choiceOfProduct = scanner.nextInt();

                    if (choiceOfProduct == 1 || choiceOfProduct == 2 || choiceOfProduct == 0){
                        validProductChoice = true;
                    } else {
                        System.out.println("Invalid Choice (1, 2 or 0)\n");
                        scanner.nextLine();
                    }
                } else {
                    System.out.println("Invalid Input data type. Enter an integer!\n");
                    scanner.nextLine();
                }
            }
            scanner.nextLine();
            int moreProducts = 1;

            while (moreProducts == 1) {
                if (choiceOfProduct == 1) {

                    Electronics electronics = new Electronics();

                    electronics.setProductID(UUID.randomUUID().toString().substring(0, 4));

                    boolean isValidName = false;
                    while (!isValidName) {
                        System.out.print("Enter the Name of the Electronic: ");
                        electronics.setProductName(scanner.nextLine());

                        if (electronics.getProductName().isEmpty()) {
                            System.out.println("Electronic Name Can Not Be Empty!\n");
                        } else {
                            try {
                                Integer.parseInt(electronics.getProductName());
                                System.out.println("Enter a String for Name Please!\n");
                            } catch (NumberFormatException e) {
                                isValidName = true;
                            }
                        }
                    }

                    // number of items & validation
                    boolean validItemsInput = false;
                    while (!validItemsInput) {
                        System.out.print("Enter the Number of Items available for the Electronic: ");
                        if (scanner.hasNextInt()) {
                            electronics.setNoAvailableItems(scanner.nextInt());
                            validItemsInput = true;
                        } else {
                            System.out.println("Invalid Input data type. Enter an integer!\n");
                            scanner.nextLine();
                        }
                    }

                    // price & validation
                    boolean isValidPrice = false;
                    while (!isValidPrice) {
                        System.out.print("Enter the Price of the Electronic: ");
                        if (scanner.hasNext()) {
                            String input = scanner.next();

                            try {
                                electronics.setPrice(Double.parseDouble(input)); // setter usage
                                isValidPrice = true;

                            } catch (NumberFormatException e) {
                                System.out.println("Invalid Input data type. Enter an integer!\n");
                            }
                        }
                    }

                    // brand & validation
                    boolean validBrand = false;

                    while (!validBrand) {
                        System.out.print("Enter the Brand of the Electronic: ");
                        electronics.setBrand(scanner.next());

                        if (electronics.getBrand().isEmpty()) {
                            System.out.println("Electronic Brand Can Not Be Empty!\n");
                        } else {
                            try {
                                Integer.parseInt(electronics.getBrand());
                                System.out.println("Enter a String for Brand Please!\n");
                            } catch (NumberFormatException e) {
                                validBrand = true;
                            }
                        }
                    }

                    // warranty option & validation
                    boolean isWarrantyOptionValid = false;
                    int warrantyOption = 0;

                    while (!isWarrantyOptionValid) {
                        System.out.println("Enter the Warranty Period of the Electronic: ");
                        System.out.println("1. In Weeks? ");
                        System.out.println("2. In Months? ");
                        System.out.println("3. In Years? ");

                        System.out.print("\nEnter your preferred option: ");
                        if (scanner.hasNextInt()) {
                            warrantyOption = scanner.nextInt();
                            electronics.setWarrantyPeriod(warrantyOption);

                            if (warrantyOption > 0 && warrantyOption < 4) {
                                isWarrantyOptionValid = true;
                            } else {
                                System.out.println("Invalid Option, Please Enter Between 1 to 3!");
                            }
                        } else {
                            System.out.println("Invalid Input Data Type!");
                            scanner.nextLine();
                        }
                    }

                    // warranty time period & validation
                    boolean warrantyValid = false;

                    switch (warrantyOption) {
                        case 1 -> {
                            while (!warrantyValid) {
                                System.out.print("Enter the warranty in weeks: ");

                                if (scanner.hasNextInt()) {
                                    electronics.setWarrantyPeriod(scanner.nextInt());
                                    electronics.setWarrantyTimeSelection("Week/s");
                                    warrantyValid = true;
                                } else {
                                    System.out.println("Invalid Input.");
                                    scanner.nextLine();
                                }
                            }
                        }

                        case 2 -> {
                            while (!warrantyValid) {
                                System.out.print("Enter the warranty in months: ");

                                if (scanner.hasNextInt()) {
                                    electronics.setWarrantyPeriod(scanner.nextInt());
                                    electronics.setWarrantyTimeSelection("Month/s");

                                    warrantyValid = true;
                                } else {
                                    System.out.println("Invalid Input.");
                                    scanner.nextLine();
                                }
                            }
                        }
                        case 3 -> {
                            while (!warrantyValid) {
                                System.out.print("Enter the warranty in years: ");

                                if (scanner.hasNextInt()) {
                                    electronics.setWarrantyPeriod(scanner.nextInt());
                                    electronics.setWarrantyTimeSelection("Year/s");

                                    warrantyValid = true;
                                } else {
                                    System.out.println("Invalid Input.");
                                    scanner.nextLine();
                                }
                            }
                        }
                        default -> System.out.println("Enter a valid input");

                    }

                    scanner.nextLine();

                    // if products exceed limit - validation
                    if (cart.getProductsList().size() >= 50) {
                        System.out.println("Adding UNSUCCESSFUL! Total products exceeding the limit.");
                    } else {
                        Electronics newElectronics = new Electronics(electronics.getProductID(), electronics.getProductName(),
                                electronics.getNoAvailableItems(), electronics.getPrice(), electronics.getBrand(),
                                electronics.getWarrantyPeriod(), electronics.getWarrantyTimeSelection());
                        cart.addProduct(newElectronics);
                        System.out.println("\nSystem have added the Electronic Product Successfully!");
                    }

                    boolean validMoreProducts = false;
                    while (!validMoreProducts) {
                        System.out.print("\nDo you want to add more clothing products(yes = 1 | no = 0)? ");
                        if (scanner.hasNextInt()) {

                            moreProducts = scanner.nextInt();
                            validMoreProducts = true;
                        } else {
                            System.out.println("Invalid Input data type. Enter an integer!\n");
                            scanner.nextLine();
                        }
                    }

                } else if (choiceOfProduct == 2) {

                    Clothing clothing = new Clothing();

                    clothing.setProductID(UUID.randomUUID().toString().substring(0, 4));

                    // name & validation
                    boolean isValidName = false;
                    while (!isValidName) {
                        System.out.print("\nEnter name of the Clothing: ");
                        clothing.setProductName(scanner.nextLine());

                        if (clothing.getProductName().isEmpty()) {
                            System.out.println("Clothing Name Can Not Be Empty!");
                        } else {
                            try {
                                Integer.parseInt(clothing.getProductName());
                                System.out.println("Enter a String for Name Please!");
                            } catch (NumberFormatException e) {
                                isValidName = true;
                            }
                        }
                    }

                    // number of items & validation
                    boolean validItemsInput = false;
                    while (!validItemsInput) {
                        System.out.print("Enter the Number of Items available for the Clothing: ");
                        if (scanner.hasNextInt()) {
                            clothing.setNoAvailableItems(scanner.nextInt());

                            validItemsInput = true;
                        } else {
                            System.out.println("Invalid Input data type. Enter an integer!\n");
                            scanner.nextLine();
                        }
                    }

                    // price & validation
                    boolean isValidPrice = false;
                    while (!isValidPrice) {
                        System.out.print("Enter the Price of the Clothing: ");
                        if (scanner.hasNext()) {
                            String input = scanner.next();

                            try {
                                clothing.setPrice(Double.parseDouble(input));
                                isValidPrice = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid Input data type. Enter an integer!\n");
                            }
                        }
                    }
                    scanner.nextLine();

                    // size & validation
                    List<String> sizesList = Arrays.asList("S", "M", "L", "XL", "XXL");
                    boolean validSize = false; // initialize at first as false

                    do {
                        System.out.print("\nAvailable Sizes - [\"S\", \"M\", \"L\", \"XL\", \"XXL\"]\nEnter size of the Clothing: ");
                        clothing.setSize(scanner.nextLine());

                        if (sizesList.contains(clothing.getSize().toUpperCase())){
                            validSize = true;
                        }

                        if (!validSize) System.out.println("Enter a valid size!");
                    } while(!validSize);


                    // color & validation
                    boolean isValidColor = false;
                    while (!isValidColor) {
                        System.out.print("\nEnter the color of the Clothing: ");
                        clothing.setColor(scanner.nextLine());

                        if (clothing.getColor().isEmpty()) {
                            System.out.println("Electronic Name Can Not Be Empty!");
                        } else {
                            try {
                                Integer.parseInt(clothing.getColor());
                                System.out.println("Enter a String for Name Please!");
                            } catch (NumberFormatException e) {
                                isValidColor = true;
                            }
                        }
                    }

                    //if (((productList.size() + noAvailableItems)) > 50) {
                    if (cart.getProductsList().size() >= 50) {
                        System.out.println("Adding UNSUCCESSFUL! Total products exceeding the limit.");
                    } else {
                        Clothing newClothing = new Clothing(clothing.getProductID(), clothing.getProductName(),
                                clothing.getNoAvailableItems(), clothing.getPrice(), clothing.getSize(), clothing.getColor());
                        cart.addProduct(newClothing);
                        System.out.println("\nSystem have added the Clothing Product Successfully!");
                    }

                    boolean validMoreProducts = false;
                    while (!validMoreProducts) {
                        System.out.print("\nDo you want to add more clothing products(yes = 1 | no = 0)? ");
                        if (scanner.hasNextInt()) {

                            moreProducts = scanner.nextInt();
                            validMoreProducts = true;
                        } else {
                            System.out.println("Invalid Input data type. Enter an integer!\n");
                            scanner.nextLine();
                        }
                    }
                } else break;
            }
        }
    }

    // overriding the removeProduct to set the method
    @Override
    public void removeProduct() {
        System.out.print("Enter the ID of the product to be deleted: ");
        String proIDToRemove = scanner.nextLine();

        Product productToRemove = null;

        for (Product product : cart.getProductsList()) {
            if (product.getProductID().equals(proIDToRemove)) {
                productToRemove = product;
                cart.removeProduct(productToRemove);
                System.out.println(productToRemove.getProductType() + " product with ID " + proIDToRemove + " have been"
                        + " removed! Total number of Products remaining is: " + cart.getProductsList().size());
                break;
            }
        }

        if (productToRemove == null) System.out.println("Product with ID " + proIDToRemove + " is not available!");
    }

    // overriding the displayProduct to set the method
    @Override
    public void displayProducts() {
        int i = 0;

        // sorting
        cart.getProductsList().sort(Comparator.comparing(Product::getProductID));

        // displaying msg if list is empty
        if (cart.getProductsList().size() == 0) System.out.println("The list is currently empty");

        // printing the items
        for (Product product : cart.getProductsList()) {
            i++;
            if (product.getNoAvailableItems()>0) {
                System.out.print("\n"+ i + ". [ID]-" + product.getProductID() + " | [Name]-" + product.getProductName()
                        + " | [Type]-" + product.getProductType() + " | [Price]-$" + product.getPrice());

                if (product instanceof Electronics electronicsProduct) {
                    System.out.print(" | [Brand]-" + electronicsProduct.getBrand() + " | [Warranty Period]" +
                            electronicsProduct.getWarrantyTimeSelection() +  "- " + electronicsProduct.getWarrantyPeriod());
                } else if (product instanceof Clothing clothingProduct) {
                    System.out.print(" | [Size]-" + clothingProduct.getSize() + " | [Color]-" + clothingProduct.getColor());
                }

                System.out.println(" | [no available items]- " + product.getNoAvailableItems());
            }
        }
    }

    // overriding the saveToFile to set the method
    @Override
    public void saveToFile() throws IOException {
        String fileName = "data";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Product product : cart.getProductsList()) {
                writer.write(
                        product.getProductID() + " - " + product.getProductName() + " - " +
                                product.getProductType() + " - " + product.getPrice() + " - " + product.getNoAvailableItems()
                );

                if (product instanceof Electronics electronicsProduct) {
                    writer.write(" - " + electronicsProduct.getBrand() + " - " +
                            electronicsProduct.getWarrantyPeriod() + " - " + electronicsProduct.getWarrantyTimeSelection());
                } else if (product instanceof Clothing clothingProduct) {
                    writer.write(" - " + clothingProduct.getSize() + " - " + clothingProduct.getColor());
                }
                writer.newLine();
            }
            System.out.println("The data has been successfully saved in the file named " + fileName);
        }
    }

    boolean alreadyRetrieved = false;
    // overriding the retrieve data to set the method
    @Override
    public void retrieveData() throws IOException {

        if (!alreadyRetrieved) {
            try (BufferedReader reader = new BufferedReader(new FileReader("data"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" - ");

                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String type = parts[2].trim();
                    double price = Double.parseDouble(parts[3].trim());
                    int noOfAvailableItems = Integer.parseInt(parts[4].trim());

                    Product product = null;

                    if ("Electronic".equals(type)) {
                        String brand = parts[5].trim();
                        int warrantyPeriod = Integer.parseInt(parts[6].trim());
                        String warrantyTimeSelection = parts[7].trim();
                        if (cart.getProductsList().size() < 50) {
                            product = new Electronics(id, name, noOfAvailableItems, price, brand, warrantyPeriod,
                                    warrantyTimeSelection);
                        } else {
                            System.out.println("error");
                            break;
                        }

                    } else if ("Clothing".equals(type)) {

                        String size = parts[5].trim();
                        String color = parts[6].trim();

                        if (cart.getProductsList().size() < 50) {
                            product = new Clothing(id, name, noOfAvailableItems, price, size, color);
                        } else {
                            System.out.println("error");
                            break;
                        }
                    }
                    cart.getProductsList().add(product);
                    alreadyRetrieved = true;
                }
                System.out.println("Retrieved Successfully");
            }
        } else System.out.println("You have already retrieved the data!");
    }

    // overriding the totalCostCalc to set the method
    @Override
    public void totalCostCalc() {
        System.out.println("Total Cost is $" + cart.totalCostCalc());
    }

    @Override
    public void clearList() {
        boolean isConfirmationValid = false;

        // confirmation for clearing list
        while (!isConfirmationValid) {
            System.out.print("\nConfirm if you want to remove all the products (y/n): ");
            String confirmation = scanner.next().toLowerCase();

            if (confirmation.equals("y")) {
                cart.clearList();
                System.out.println("Cart Successfully Cleared!");
                isConfirmationValid = true;
            } else if (confirmation.equals("n")) {
                System.out.println("Returning Back To Menu");
                isConfirmationValid = true;
            } else {
                System.out.println("Invalid Input! Enter Either y/n");
            }
        }
    }
}