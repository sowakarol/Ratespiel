package pl.edu.agh.kis.panels;

import javax.swing.*;

/**
 * Created by Karl on 25.01.2017.
 * Panel informing player that he must be ready in wait for game seconds
 */
public class GetReadyPanel extends JPanel {
    /**
     * @param a seconds in which the game will be started
     */
    public GetReadyPanel(int a, int numberOfOtherPlayers) {
        super();
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("Get Ready in next  " + a + " seconds the game will start!!" + "You and " + numberOfOtherPlayers + "more players will play!");
        textPane.setEditable(false);
        add(textPane);
    }
}
