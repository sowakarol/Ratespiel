package pl.edu.agh.kis.panels;

import javax.swing.*;

/**
 * Created by Karl on 15.01.2017.
 * Panel when player fail the game
 */
public class FailPanel extends JPanel {
    public FailPanel() {
        super();
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("You lost :(");
        textPane.setEditable(false);
        add(textPane);
    }

}
