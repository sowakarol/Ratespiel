package pl.edu.agh.kis.View;

import pl.edu.agh.kis.Controller.LoginController;

import javax.swing.*;

/**
 * Created by Karl on 14.01.2017.
 */
public class LoginPanel extends JPanel {
    boolean initialized;
    JLabel userLabel;
    JLabel portLabel;
    String username;
    int portNumber;
    LoginController loginController;

    public LoginFrame(LoginController loginController) {
        super("Log in");
        initialized = false;
        this.loginController = loginController;
    }

    public JLabel getUserLabel() {
        return userLabel;
    }

    public JLabel getPortLabel() {
        return portLabel;
    }

    private void placeButtons(JPanel panel) {

        /* We will discuss about layouts in the later sections
         * of this tutorial. For now we are setting the layout
         * to null
         */
        panel.setLayout(null);

        // Creating JLabel
        this.userLabel = new JLabel("Username");
        /* This method specifies the location and size
         * of component. setBounds(x, y, width, height)
         * here (x,y) are cordinates from the top left
         * corner and remaining two arguments are the width
         * and height of the component.
         */
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        /* Creating text field where user is supposed to
         * enter user name.
         */
        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        // Same process for password label and text field.
        JLabel portLabel = new JLabel("Port number");
        portLabel.setBounds(10, 50, 80, 25);
        panel.add(portLabel);

        /*This is similar to text field but it hides the user
         * entered data and displays dots instead to protect
         * the password like we normally see on login screens.
         */
        JTextField portText = new JTextField(20);
        portText.setBounds(100, 50, 165, 25);
        panel.add(portText);

        // Creating login button
        JButton loginButton = new JButton("Submit");
        loginButton.addActionListener(loginController);

        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);
    }

    public void set() {
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel panel = new JPanel();
        add(panel);

        placeButtons(panel);

        setVisible(true);
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }
}
