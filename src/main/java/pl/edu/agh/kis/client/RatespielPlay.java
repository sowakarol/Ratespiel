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
            Socket playerSocket = new Socket(main.getLoginController().getHostname(), main.getLoginController().getPortNumber());
            player = new ClientSidePlayer(playerSocket);
            player.getHelloFromServer();


        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 4; i++) {
            player.playRound();
        }

        player.getResult();

        player.closeConnection();

    }
}
