package pl.edu.agh.kis.panels;

import javax.swing.*;

/**
 * Created by Karl on 27.01.2017.
 */
public class DrawPanel extends JPanel {
    public DrawPanel(int howManyPlayersDraw) {
        super();
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("You draw with " + howManyPlayersDraw + " other players");
        textPane.setEditable(false);
        add(textPane);
    }
}
