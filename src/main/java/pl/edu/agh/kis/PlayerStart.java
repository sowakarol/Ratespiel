package pl.edu.agh.kis;

import pl.edu.agh.kis.Controller.MainController;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.player.PlayerClientSideWithGUIAbstract;

/**
 * Created by Karl on 14.01.2017.
 */
public class PlayerStart {
    MainController main;
    PlayerClientSideWithGUIAbstract player;

    public static void main(String[] args) {
        PlayerStart playerStart = new PlayerStart();
        playerStart.startPlaying();


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

        player = main.getLoginController().getPlayer();

        for (int i = 0; i < 4; i++) {
            player.playRound();
        }

        player.getResult();

        player.closeConnection();

    }
}
