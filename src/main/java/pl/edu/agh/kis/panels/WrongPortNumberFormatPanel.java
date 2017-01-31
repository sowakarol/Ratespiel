package pl.edu.agh.kis.panels;

import javax.swing.*;

/**
 * Created by Karl on 30.01.2017.
 * panel informing player that he putted not a number in portText JTextField
 */
public class WrongPortNumberFormatPanel extends JPanel {
    public WrongPortNumberFormatPanel() {
        super();
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("Port Number MUST be a Number!");
        textPane.setEditable(false);
        add(textPane);
    }


}
