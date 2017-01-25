package pl.edu.agh.kis.panels;

import javax.swing.*;

/**
 * Created by Karl on 25.01.2017.
 */
public class GetReadyPanel extends JPanel {
    public GetReadyPanel(int a) {
        super();
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("Get Ready in next  " + a + " seconds the game will start!!");
        textPane.setEditable(false);
        add(textPane);
    }
}
