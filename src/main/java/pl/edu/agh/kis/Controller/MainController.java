package pl.edu.agh.kis.Controller;

import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.client.ClientSidePlayer;
import pl.edu.agh.kis.messages.client.DisconnectPlayerMessage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Karl on 14.01.2017.
 */
public class MainController implements ActionListener {
    private ClientSidePlayer player;
    private MainFrame mainFrame;
    private boolean mainControllerClicked = false;
    private LoginController loginController;
    private boolean exitWithoutPlaying = false;


    public MainController() {

    }

    public ClientSidePlayer getPlayer() {
        return player;
    }

    public void setPlayer(ClientSidePlayer player) {
        this.player = player;
    }


    public void setMainControllerClicked(boolean mainControllerClicked) {
        this.mainControllerClicked = mainControllerClicked;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public boolean isExitWithoutPlaying() {
        return exitWithoutPlaying;
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
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        loginController = new LoginController(mainFrame);
    }

    public void disconnectPlayerWhenClosingWindow() {
        if (player != null) {
            if (player.isConnected()) {
                player.setConnected(false);
                System.out.println("uuuu");
                new DisconnectPlayerMessage(getPlayer().getOutputStream()).send();
                getPlayer().closeConnection();
            }
        } else {
            exitWithoutPlaying = true;
        }
    }

}
