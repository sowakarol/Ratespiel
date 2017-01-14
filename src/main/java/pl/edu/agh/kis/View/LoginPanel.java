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
    JTextField userText;
    JTextField portText;
    LoginController loginController;

    public LoginPanel(LoginController loginController) {
        super();
        initialized = false;
        this.loginController = loginController;
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();

        //LoginPanel loginPanel = new LoginPanel();


        //jFrame.add(loginPanel);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public JLabel getUserLabel() {
        return userLabel;
    }

    public JLabel getPortLabel() {
        return portLabel;
    }

    public JTextField getUserText() {
        return userText;
    }

    public JTextField getPortText() {
        return portText;
    }

    private void placeButtons() {

        /* We will discuss about layouts in the later sections
         * of this tutorial. For now we are setting the layout
         * to null
         */
        setLayout(null);

        // Creating JLabel
        this.userLabel = new JLabel("Username");
        /* This method specifies the location and size
         * of component. setBounds(x, y, width, height)
         * here (x,y) are cordinates from the top left
         * corner and remaining two arguments are the width
         * and height of the component.
         */
        userLabel.setBounds(10, 20, 80, 25);
        add(userLabel);

        /* Creating text field where user is supposed to
         * enter user name.
         */
        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        add(userText);

        // Same process for password label and text field.
        JLabel portLabel = new JLabel("Port number");
        portLabel.setBounds(10, 50, 80, 25);
        add(portLabel);

        /*This is similar to text field but it hides the user
         * entered data and displays dots instead to protect
         * the password like we normally see on login screens.
         */
        portText = new JTextField(20);
        portText.setBounds(100, 50, 165, 25);
        add(portText);

        // Creating login button
        JButton loginButton = new JButton("Submit");
        loginButton.addActionListener(loginController);

        loginButton.setBounds(10, 80, 80, 25);
        add(loginButton);

    }

    public void set() {
        setSize(350, 200);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //JPanel panel = new JPanel();
        //add(panel);

        placeButtons();

        setVisible(true);
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
