package pl.edu.agh.kis.Controller;

import pl.edu.agh.kis.Client;
import pl.edu.agh.kis.View.LoginFrame;
import pl.edu.agh.kis.View.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Karl on 14.01.2017.
 */
public class MainController implements ActionListener {
    MainFrame mainFrame;
    Client client;
    String username;
    int portNumber;

    public static void main(String[] args) {
        MainController main = new MainController();
        MainFrame mainFrame = new MainFrame(main);
        mainFrame.setDefault();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //LoginCreation loginCreation = new LoginCreation();

        Thread thread = new Thread(new LoginCreation());
        thread.start();
        while (!loginCreation.loginFrame.isInitialized()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        portNumber = loginCreation.loginController.getLoginFrame().getPortNumber();
        username = loginCreation.loginController.getLoginFrame().getUsername();
        client = new Client(portNumber, "localhost");
    }

    class LoginCreation implements Runnable {
        public LoginFrame loginFrame;
        LoginController loginController;

        LoginCreation() {
            loginController = new LoginController();
            loginFrame = new LoginFrame(loginController);
        }

        @Override
        public void run() {
            LoginController loginController = new LoginController();
            LoginFrame loginFrame;
            loginFrame = new LoginFrame(loginController);
            loginFrame.set();
        }
    }
}
