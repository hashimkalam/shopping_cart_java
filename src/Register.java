import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Register {
    static JTextField usernameArea;
    static JPasswordField passwordField;
    public static void register(List<Product> productList) {
        JFrame registerFrame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBackground(new Color(230, 230, 255));
        JPanel buttonsPanel  = new JPanel();
        panel.setBorder(new EmptyBorder(40, 160, 40, 160));
        userValidation("", "", false);
        panel.setLayout(new GridLayout(7, 1));

        Font myFont = new Font("SansSerif", Font.BOLD, 14);

        // username
        JLabel usernameLabel = new JLabel("Enter Username");
        usernameLabel.setFont(myFont);
        panel.add(usernameLabel);
        usernameArea = new JTextField();
        panel.add(usernameArea);

        // password
        JLabel passwordLabel = new JLabel("Enter Password");
        passwordLabel.setFont(myFont);
        panel.add(passwordLabel);
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // error message
        JLabel errorMsg = new JLabel("");
        errorMsg.setForeground(Color.red);  // change msg color to red
        errorMsg.setHorizontalAlignment(JLabel.CENTER); // center text
        panel.add(errorMsg);

        panel.add(buttonsPanel);
        buttonsPanel.setBackground(new Color(235, 235, 255));

        // login & register button respectively
        JButton loginBtn = new JButton("Login");
        buttonsPanel.add(loginBtn);
        JButton signupBtn = new JButton("Signup");
        buttonsPanel.add(signupBtn);

        loginProcess(loginBtn, errorMsg, productList, registerFrame); // call login method
        signUpProcess(signupBtn, errorMsg, productList, registerFrame);  // call signup method

        registerFrame.setLayout(new BorderLayout());
        registerFrame.add(panel, BorderLayout.CENTER);
        registerFrame.setSize(750, 500);
        registerFrame.setVisible(true);
        System.out.println("GUI successfully opened!");
    }

    private static void loginProcess(JButton loginBtn, JLabel errorMsg, List<Product> productList, JFrame registerFrame) {
        loginBtn.addActionListener(e -> {
            boolean isLogin = true;

            // storing the data
            String usernameData = usernameArea.getText();
            String passwordData = passwordField.getText();

            if (userValidation(usernameData, passwordData, isLogin)) {
                // navigate to this frame when button click
                GUISection.shoppingManagerGui(productList);
                // close the container when button click
                registerFrame.dispose();
            } else if (usernameData.equals("") || passwordData.equals("")) {
                errorMsg.setText("Username or Password can not be empty");
            } else {
                errorMsg.setText("Incorrect Credentials");
            }
        });
    }

    public static void signUpProcess(JButton registerBtn, JLabel errorMsg, List<Product> productList, JFrame registerFrame) {

        User user = new User();

        registerBtn.addActionListener(e -> {
            boolean isLogin = false;
            // storing the data
            String usernameData = usernameArea.getText();
            user.setUsername(usernameData);
            String passwordData = passwordField.getText();
            user.setPassword(passwordData);

            // creating a user instance
            User userInfo = new User(user.getUsername(), user.getPassword());

            if (userValidation(userInfo.getUsername(), userInfo.getPassword(), isLogin)) {
                errorMsg.setText("Username must be unique");
            } else if (userInfo.getUsername().equals("") || userInfo.getPassword().equals("")) {
                errorMsg.setText("Username or Password can not be empty");
            }else {
                try (FileWriter write = new FileWriter("userDetails", true)) {
                    write.write((userInfo.getUsername() + " - " + userInfo.getPassword()+ "\n"));
                } catch (IOException event) {
                    event.printStackTrace();
                }

                // navigate to this frame when button click
                GUISection.shoppingManagerGui(productList);

                // close the container when button click
                registerFrame.dispose();
            }
        });
    }

    public static boolean userValidation(String usernameData, String passwordData, boolean isLogin) {
        try (BufferedReader reader = new BufferedReader(new FileReader("userDetails"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");

                if (parts.length == 2) {
                    String usernameRetrieved = parts[0];
                    String passwordRetrieved = parts[1];

                    if (isLogin) {
                        if (usernameRetrieved.equals(usernameData) && passwordRetrieved.equals(passwordData)) return true;
                    } else {
                        if (usernameRetrieved.equals(usernameData)) return true;
                    }
                }
            }
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.println("No users created yet \n");
        }
        return false;
    }
}
