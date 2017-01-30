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
     *
     */
    private LoginPanel loginPanel;
    /**
     *
     */
    private boolean initialized = false;
    /**
     *
     */
    private String hostname;
    /**
     *
     */
    private String username;
    /**
     *
     */
    private int portNumber;


    public LoginController(MainFrame mainFrame) {
        loginPanel = new LoginPanel(this);
        this.mainFrame = mainFrame;
    }



    public LoginPanel getLoginPanel() {
        return loginPanel;
    }


    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public synchronized boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("XD");
        if (!clicked) {
            System.out.println("XXD");
            username = loginPanel.getUserText().getText();
            System.out.println(username);
            String tmp = loginPanel.getPortText().getText();

            try {
                portNumber = Integer.parseInt(tmp);
            } catch (NumberFormatException e1) {
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
            System.out.println(portNumber);
            hostname = loginPanel.getHostText().getText();


            initialized = true;
            System.out.println(": (");
            clicked = true;
        }

    }

}
