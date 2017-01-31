package pl.edu.agh.kis.panels;

import javax.swing.*;

/**
 * Created by Karl on 25.01.2017.
 * panel informing a player about how may points he has
 */
public class PointsPanel extends JPanel {
    public PointsPanel(int points) {
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("You have  " + points + " points");
        textPane.setEditable(false);
        add(textPane);
    }
}
