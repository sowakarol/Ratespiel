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
 */
public class RatespielPlay {
    MainController main;
    ClientSidePlayer player;
    Container container;

    public RatespielPlay() {
    }

    public static void main(String[] args) {
        RatespielPlay ratespiel = new RatespielPlay();
        ratespiel.startPlaying();
    }

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
            System.out.println(":OOOO");
            String hostname = "";
            int portNumber = 0;
            try {
                System.out.println("inner try");
                hostname = main.getLoginController().getHostname();
                portNumber = main.getLoginController().getPortNumber();
                System.out.println(hostname);
                System.out.println(portNumber);
                Socket playerSocket = new Socket(hostname, portNumber);
                player = new ClientSidePlayer(playerSocket, main);
                main.setPlayer(player);
                initialized = true;
                if (main.isExitWithoutPlaying()) break;

            } catch (Exception e) {
                if (main.isExitWithoutPlaying()) break;

                //main.getLoginController().setInitialized(false);


                main.setLoginController(new LoginController(main.getMainFrame()));
                main.setMainControllerClicked(false);
                System.out.println("xd");
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

        player.play();
        //player.getResult();
        player.closeConnection();

    }

    private void initialize() {
    }

}
