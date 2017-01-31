package pl.edu.agh.kis.panels;

import javax.swing.*;

/**
 * Created by Karl on 15.01.2017.
 * panel informing a player that he won a game
 */
public class WinPanel extends JPanel {

    public WinPanel() {
        super();
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("You won!");
        textPane.setEditable(false);
        add(textPane);
    }

}
