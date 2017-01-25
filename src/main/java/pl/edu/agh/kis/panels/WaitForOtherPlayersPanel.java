package pl.edu.agh.kis.panels;

import javax.swing.*;

/**
 * Created by Karl on 25.01.2017.
 */
public class WaitForOtherPlayersPanel extends JPanel {
    public WaitForOtherPlayersPanel(int howManyOtherPlayers) {
        super();
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        String singularOrPlural = "player";
        if (howManyOtherPlayers > 1) {
            singularOrPlural = "players";
        }
        textPane.setText("Wait for " + howManyOtherPlayers + " " + singularOrPlural);
        textPane.setEditable(false);
        add(textPane);
    }
}
