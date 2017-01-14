package pl.edu.agh.kis.View;

import pl.edu.agh.kis.Controller.WaitingController;

import javax.swing.*;

/**
 * Created by Karl on 14.01.2017.
 */
public class WaitingPanel extends JPanel {
    MainFrame mainFrame;
    WaitingController waitingController;

    WaitingPanel(MainFrame mainFrame, WaitingController waitingController) {
        this.mainFrame = mainFrame;
        this.waitingController = waitingController;
    }


}
