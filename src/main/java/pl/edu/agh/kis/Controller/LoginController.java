package pl.edu.agh.kis.Controller;

import pl.edu.agh.kis.View.LoginPanel;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.panels.WrongPortNumberFormatPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Karl on 14.01.2017.
 * Class taking care of an input from client in logging panel
 */
public class LoginController implements ActionListener {
    /**
     * true if Submit button was clicked
     */
    private boolean clicked = false;
    /**
     * logging player mainFrame
     */
    private MainFrame mainFrame;
    /**
     * loginPanel of a player who is logging
     */
    private LoginPanel loginPanel;
    /**
     * true if everything was initialized corrected
     */
    private boolean initialized = false;
    /**
     * server's hostname
     */
    private String hostname;
    /**
     * username of player
     */
    private String username;
    /**
     * portNumber of server
     */
    private int portNumber;

    /**
     * @param mainFrame mainFrame of logging player
     */
    public LoginController(MainFrame mainFrame) {
        loginPanel = new LoginPanel(this);
        this.mainFrame = mainFrame;
    }


    /**
     * getter of LoginPanel
     *
     * @return reference to LoginPanel
     */
    public LoginPanel getLoginPanel() {
        return loginPanel;
    }


    /**
     * getter of initialized
     * @return value of initialized variable
     */
    public synchronized boolean isInitialized() {
        return initialized;
    }

    /**
     * getter of hostname
     * @return hostname string value
     */
    public String getHostname() {
        return hostname;
    }


    /**
     * getter of hostname variable
     * for future functionalities
     * @return username value
     */
    public String getUsername() {
        return username;
    }


    /**
     * getter of portNumber value
     * @return portNumber value
     */
    public int getPortNumber() {
        return portNumber;
    }

    /**
     * taking care of a logging session - gets input data, checking if it's correct
     * @param e buttons connected to this action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!clicked) {
            username = loginPanel.getUserText().getText();
            System.out.println(username);
            String tmp = loginPanel.getPortText().getText();

            try {
                portNumber = Integer.parseInt(tmp);
            } catch (NumberFormatException e1) {
                //when cannot parseInt
                e1.printStackTrace();
                Dimension d = mainFrame.getSize();
                WrongPortNumberFormatPanel wrongPortNumberFormatPanel = new WrongPortNumberFormatPanel();
                wrongPortNumberFormatPanel.setBounds(10, 155, 80, 25);
                mainFrame.getContentPane().add(wrongPortNumberFormatPanel);
                mainFrame.pack();
                mainFrame.validate();
                mainFrame.repaint();
                mainFrame.setSize(d);
                mainFrame.setVisible(true);
                return;
            }
            hostname = loginPanel.getHostText().getText();

            initialized = true;
            clicked = true;
        }

    }

}
