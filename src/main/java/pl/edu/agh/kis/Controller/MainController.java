package pl.edu.agh.kis.Controller;

import pl.edu.agh.kis.View.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

/**
 * Created by Karl on 14.01.2017.
 */
public class MainController implements ActionListener {
    String username;
    int portNumber;
    Socket socket;
    private MainFrame mainFrame;
    private boolean mainControllerClicked = false;
    private LoginController loginController;

    //observer albo interferjs jeszcze między buttonem
    public MainController() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainController main = new MainController();
                MainFrame mainFrame = new MainFrame(main);
                main.setMainFrame(mainFrame);
                mainFrame.setDefault();

            }
        });

    }

    public LoginController getLoginController() {
        return loginController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!mainControllerClicked) {
            //LoginCreation loginCreation = new LoginCreation();
            Container container = mainFrame.getContentPane();
            container.removeAll();
            //mainFrame.revalidate();

            //mainFrame.repaint();
            //mainFrame.revalidate();
            loginController.getLoginPanel().set();
            container.add(loginController.getLoginPanel());
            //mainFrame.repaint();
            //mainFrame.revalidate();
            mainFrame.validate();
            mainFrame.repaint();
            mainFrame.setVisible(true);


            mainControllerClicked = true;
        }
        /*portNumber = loginController.getLoginPanel().getPortNumber();
        username = loginController.getLoginPanel().getUsername();
        client = new Client(portNumber, "localhost");
        socket = client.getPlayerSocket();
        player = new PlayerClientSideWithGUI(socket,mainFrame);*/
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        loginController = new LoginController(mainFrame);
    }

    /*class LoginCreation implements Runnable {
        public LoginFrame loginFrame;
        LoginController loginController;

        LoginCreation() {
            loginController = new LoginController();
            loginPanel = new LoginPanel(loginController);
        }

        @Override
        public void run() {
            LoginController loginController = new LoginController();
            LoginFrame loginFrame;
            loginFrame = new LoginFrame(loginController);
            loginFrame.set();
        }
    }*/
}
