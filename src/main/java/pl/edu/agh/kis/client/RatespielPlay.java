package pl.edu.agh.kis.client;

import pl.edu.agh.kis.Controller.MainController;
import pl.edu.agh.kis.View.MainFrame;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Karl on 22.01.2017.
 */
public class RatespielPlay {
    MainController main;
    ClientSidePlayer player;

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


        while (!main.getLoginController().isInitialized()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        try {
            String s = main.getLoginController().getHostname();
            int i = main.getLoginController().getPortNumber();
            System.out.println(i);
            System.out.println(s);
            Socket playerSocket = new Socket(s, main.getLoginController().getPortNumber());
            player = new ClientSidePlayer(playerSocket, main);
            //player.getHelloFromServer();


        } catch (IOException e) {
            e.printStackTrace();
        }

        player.play();

        //player.getResult();

        player.closeConnection();

    }


}
