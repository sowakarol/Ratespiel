package pl.edu.agh.kis.client;

import pl.edu.agh.kis.Controller.LoginController;
import pl.edu.agh.kis.Controller.MainController;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.panels.CannotReachServerPanel;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

/**
 * Created by Karl on 22.01.2017.
 * Class starting a game. Represents client side Main GUI Panel and taking actions like connecting to a server.
 * When player is ready to play, it starts the game on a Client side.
 */
public class RatespielPlay {
    /**
     * Main Controller of a player
     */
    private MainController main;
    /**
     * player which will be initialized
     */
    private ClientSidePlayer player;
    /**
     * variable handling cleaning GUI
     */
    private Container container;

    /**
     * method starting game on a Client side
     *
     * @param args not needed
     */
    public static void main(String[] args) {
        RatespielPlay ratespiel = new RatespielPlay();
        ratespiel.startPlaying();
    }

    /**
     * method which makes GUI, taking care with LoginController on a connecting to a server
     */
    private void startPlaying() {
        main = new MainController();
        MainFrame mainFrame = new MainFrame(main);
        main.setMainFrame(mainFrame);
        mainFrame.setDefault();
        boolean initialized = false;
        do {
            while (!main.getLoginController().isInitialized()) {
                try {
                    Thread.sleep(500);
                    if (main.isExitWithoutPlaying()) break;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String hostname = "";
            int portNumber = 0;
            try {
                //trying to connect with data inputted to GUI
                hostname = main.getLoginController().getHostname();
                portNumber = main.getLoginController().getPortNumber();
                System.out.println(hostname);
                System.out.println(portNumber);
                Socket playerSocket = new Socket(hostname, portNumber);
                player = new ClientSidePlayer(playerSocket, main);
                main.setPlayer(player);
                initialized = true;
                if (main.isExitWithoutPlaying()) break;

            } catch (RuntimeException e1) {
                e1.printStackTrace();
            } catch (Exception e) {

                if (main.isExitWithoutPlaying()) break;

                //creating a panel which can go back to logging panel in case of bad data inputted
                main.setLoginController(new LoginController(main.getMainFrame()));
                main.setMainControllerClicked(false);
                e.printStackTrace();
                Dimension d = main.getMainFrame().getSize();

                container = main.getMainFrame().getContentPane();
                container.removeAll();

                JButton b1 = new JButton("Try again!");
                b1.setVerticalTextPosition(AbstractButton.CENTER);
                b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
                b1.setActionCommand("disable");
                b1.addActionListener(main);
                main.getMainFrame().getContentPane().add(b1);
                main.getMainFrame().pack();
                main.getMainFrame().getContentPane().add(new CannotReachServerPanel(hostname, portNumber));
                main.getMainFrame().pack();
                main.getMainFrame().validate();
                main.getMainFrame().repaint();
                main.getMainFrame().setSize(d);
                main.getMainFrame().setVisible(true);

            }
        } while (!initialized);

        if (main.isExitWithoutPlaying()) return;


        //in case when everything is OK, get player to play a game
        player.play();
        player.closeConnection();

    }


}
