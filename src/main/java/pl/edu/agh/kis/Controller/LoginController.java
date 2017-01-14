package pl.edu.agh.kis.Controller;

import pl.edu.agh.kis.Client;
import pl.edu.agh.kis.Model.LoginModel;
import pl.edu.agh.kis.PlayerClientSideWithGUI;
import pl.edu.agh.kis.View.LoginPanel;
import pl.edu.agh.kis.View.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

/**
 * Created by Karl on 14.01.2017.
 */
public class LoginController implements ActionListener {
    MainFrame mainFrame;
    LoginModel loginModel;
    LoginPanel loginPanel;

    volatile boolean initialized = false;
    Client client;
    String username;
    int portNumber;
    Socket socket;
    PlayerClientSideWithGUI player;

    LoginController(MainFrame mainFrame) {
        loginPanel = new LoginPanel(this);
        loginModel = new LoginModel();
        this.mainFrame = mainFrame;
    }

    public LoginController(LoginModel loginModel, LoginPanel loginPanel) {

        this.loginModel = loginModel;
        this.loginPanel = loginPanel;
    }

    public boolean init() {
        if (this.loginPanel != null) {
            loginPanel.set();
            return true;
        } else {
            return false;
        }
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


    public PlayerClientSideWithGUI getPlayer() {
        return player;
    }


    public synchronized boolean isInitialized() {
        return initialized;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        username = loginPanel.getUserText().getText();
        System.out.println(username);
        String tmp = loginPanel.getPortText().getText();
        portNumber = Integer.parseInt(tmp);
        System.out.println(portNumber);

        client = new Client(portNumber, "localhost");
        socket = client.getPlayerSocket();
        player = new PlayerClientSideWithGUI(socket, mainFrame);
        initialized = true;
        System.out.println(": (");

    }

}
