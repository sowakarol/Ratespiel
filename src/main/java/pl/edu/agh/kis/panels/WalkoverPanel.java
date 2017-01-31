package pl.edu.agh.kis.panels;

import javax.swing.*;

/**
 * Created by Karl on 27.01.2017.
 * player informing a player that he won by a walkover
 */
public class WalkoverPanel extends JPanel {
    public WalkoverPanel() {
        super();
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("You won by a walkover");
        textPane.setEditable(false);
        add(textPane);
    }
}
