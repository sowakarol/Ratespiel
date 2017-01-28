package pl.edu.agh.kis.View;

import pl.edu.agh.kis.Controller.LoginController;

import javax.swing.*;

/**
 * Created by Karl on 14.01.2017.
 */
public class LoginPanel extends JPanel {
    JRadioButton citiesButton;
    private boolean initialized;
    private JLabel userLabel;
    private JLabel portLabel;
    private JLabel hostLabel;
    private JTextField userText;
    private JTextField portText;
    private JTextField hostText;
    private LoginController loginController;

    public LoginPanel(LoginController loginController) {
        super();
        initialized = false;
        this.loginController = loginController;
    }
    //private JRadioButton wordsButton;

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();

        //LoginPanel loginPanel = new LoginPanel();


        //jFrame.add(loginPanel);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public JTextField getHostText() {
        return hostText;
    }

    public void setHostText(JTextField hostText) {
        this.hostText = hostText;
    }

    //public JRadioButton getWordsButton() {
    //  return wordsButton;
    //}

    public JLabel getHostLabel() {
        return hostLabel;
    }

    public JRadioButton getCitiesButton() {
        return citiesButton;
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

    public void setUserText(JTextField userText) {
        this.userText = userText;
    }

    public JTextField getPortText() {
        return portText;
    }

    public void setPortText(JTextField portText) {
        this.portText = portText;
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


        hostLabel = new JLabel("Hostname");
        /* This method specifies the location and size
         * of component. setBounds(x, y, width, height)
         * here (x,y) are cordinates from the top left
         * corner and remaining two arguments are the width
         * and height of the component.
         */
        hostLabel.setBounds(10, 75, 80, 25);
        add(hostLabel);

        hostText = new JTextField(20);
        hostText.setBounds(100, 75, 165, 25);
        add(hostText);

        /* Creating text field where user is supposed to
         * enter user name.
         */
        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        add(userText);

        // Same process for password label and text field.
        portLabel = new JLabel("Port number");
        portLabel.setBounds(10, 50, 80, 25);
        add(portLabel);


        /*wordsButton = new JRadioButton("Words");
        wordsButton.setSelected(true);

        citiesButton = new JRadioButton("Cities");

        ButtonGroup group = new ButtonGroup();
        group.add(wordsButton);
        group.add(citiesButton);
        wordsButton.setBounds(10, 70, 80, 25);
        citiesButton.setBounds(10, 90, 80, 25);


        add(wordsButton);
        add(citiesButton);
        */

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

        loginButton.setBounds(10, 120, 80, 25);
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
