package pl.edu.agh.kis.panels;

import javax.swing.*;

/**
 * Created by Karl on 25.01.2017.
 * player informing a player how many players are needed to start a game
 */
public class WaitForOtherPlayersPanel extends JPanel {
    public WaitForOtherPlayersPanel(int howManyOtherPlayers, int connectedPlayers) {
        super();
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        String singularOrPlural = "player";
        if (howManyOtherPlayers > 1) {
            singularOrPlural = "players";
        }
        textPane.setText("Wait for " + howManyOtherPlayers + " " + singularOrPlural + " Connected: " + connectedPlayers);
        textPane.setEditable(false);
        add(textPane);
    }
}
