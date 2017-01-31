package pl.edu.agh.kis.panels;

import javax.swing.*;

/**
 * Created by Karl on 27.01.2017.
 * Panel when player cannot reach server
 */
public class CannotReachServerPanel extends JPanel {
    public CannotReachServerPanel(String hostname, int portNumber) {
        super();
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("Cannot reach server" + hostname + " and port " + portNumber);
        textPane.setEditable(false);
        add(textPane);
    }
}
