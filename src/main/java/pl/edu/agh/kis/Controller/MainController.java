package pl.edu.agh.kis.Controller;

import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.client.ClientSidePlayer;
import pl.edu.agh.kis.messages.client.DisconnectPlayerMessage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Karl on 14.01.2017.
 * Class taking care of changing panels after start button is clicked
 */
public class MainController implements ActionListener {
    /**
     * variable representing current player
     */
    private ClientSidePlayer player;
    /**
     * player's mainFrame
     */
    private MainFrame mainFrame;
    /**
     * true if start button was clicked
     */
    private boolean mainControllerClicked = false;
    /**
     * loginController of current player
     */
    private LoginController loginController;
    /**
     * exit when player wants to exit mainFrame
     */
    private boolean exitWithoutPlaying = false;


    /**
     * @return current ClientSidePlayer
     */
    public ClientSidePlayer getPlayer() {
        return player;
    }

    /**
     * setter of player value
     *
     * @param player new player of current MainController
     */
    public void setPlayer(ClientSidePlayer player) {
        this.player = player;
    }


    /**
     * @param mainControllerClicked new value of mainControllerClicked
     */
    public void setMainControllerClicked(boolean mainControllerClicked) {
        this.mainControllerClicked = mainControllerClicked;
    }

    /**
     * @return loginController reference
     */
    public LoginController getLoginController() {
        return loginController;
    }

    /**
     * setter of LoginController
     * @param loginController new LoginController reference
     */
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    /**
     * @return isExitWithoutPlaying value
     */
    public boolean isExitWithoutPlaying() {
        return exitWithoutPlaying;
    }

    /**
     * method handling setting new loginController after clicking start and changing panels
     * @param e button connected to this action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!mainControllerClicked) {
            Container container = mainFrame.getContentPane();
            container.removeAll();

            loginController.getLoginPanel().set();
            container.add(loginController.getLoginPanel());

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
