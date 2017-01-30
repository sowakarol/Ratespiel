package pl.edu.agh.kis.Controller;

import pl.edu.agh.kis.Model.LoginModel;
import pl.edu.agh.kis.View.LoginPanel;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.panels.WrongPortNumberFormatPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Karl on 14.01.2017.
 */
public class LoginController implements ActionListener {
    private boolean clicked = false;
    private MainFrame mainFrame;
    private LoginModel loginModel;
    private LoginPanel loginPanel;
    private boolean initialized = false;
    private String hostname;
    private String username;
    private int portNumber;
    //private Socket socket;
    //private PlayerClientSideWithGUIAbstract player;

    public LoginController(MainFrame mainFrame) {
        loginPanel = new LoginPanel(this);
        loginModel = new LoginModel();
        this.mainFrame = mainFrame;
    }

    public LoginController(LoginModel loginModel, LoginPanel loginPanel) {

        this.loginModel = loginModel;
        this.loginPanel = loginPanel;
    }


    public LoginModel getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }


    //public PlayerClientSideWithGUIAbstract getPlayer() {
    //  return player;
    //}


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
