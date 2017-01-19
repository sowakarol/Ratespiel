package pl.edu.agh.kis.Controller;

import pl.edu.agh.kis.Model.LoginModel;
import pl.edu.agh.kis.View.LoginPanel;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.player.PlayerClientSideWithGUI;
import pl.edu.agh.kis.player.PlayerClientSideWithGUIAbstract;
import pl.edu.agh.kis.player.PlayerClientSideWithGUIPhoto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Karl on 14.01.2017.
 */
public class LoginController implements ActionListener {
    boolean clicked = false;
    private MainFrame mainFrame;
    private LoginModel loginModel;
    private LoginPanel loginPanel;
    private volatile boolean initialized = false;
    private String username;
    private int portNumber;
    private Socket socket;
    private PlayerClientSideWithGUIAbstract player;

    LoginController(MainFrame mainFrame) {
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


    public PlayerClientSideWithGUIAbstract getPlayer() {
        return player;
    }


    public synchronized boolean isInitialized() {
        return initialized;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!clicked) {
            username = loginPanel.getUserText().getText();
            System.out.println(username);
            String tmp = loginPanel.getPortText().getText();
            portNumber = Integer.parseInt(tmp);
            System.out.println(portNumber);
            try {
                socket = new Socket("localhost", portNumber);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //client = new Client(portNumber, "localhost");
            //socket = client.getPlayerSocket();
            //player = new PlayerClientSideWithGUI(socket, mainFrame);
            if (loginPanel.getCitiesButton().isSelected()) {
                player = new PlayerClientSideWithGUIPhoto(socket, mainFrame);

            } else {
                player = new PlayerClientSideWithGUI(socket, mainFrame);
            }


            initialized = true;
            System.out.println(": (");
            clicked = true;
        }

    }

}
