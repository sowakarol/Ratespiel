package pl.edu.agh.kis;

import pl.edu.agh.kis.Controller.MainController;
import pl.edu.agh.kis.View.MainFrame;

/**
 * Created by Karl on 14.01.2017.
 */
public class PlayerStart {
    MainController main;
    PlayerClientSideWithGUIAbstract player;

    public static void main(String[] args) {
        PlayerStart playerStart = new PlayerStart();
        playerStart.start();


    }

    private void start() {
        main = new MainController();
        MainFrame mainFrame = new MainFrame(main);
        main.setMainFrame(mainFrame);
        mainFrame.setDefault();
        System.out.println("xdxd"
        );

        while (!main.getLoginController().isInitialized()) {
            try {
                Thread.sleep(500);
                System.out.println("still hir");
            } catch (InterruptedException e) {
            }
        }

        player = main.getLoginController().getPlayer();

        for (int i = 0; i < 20; i++) {
            player.playRound();
        }


    }
}
