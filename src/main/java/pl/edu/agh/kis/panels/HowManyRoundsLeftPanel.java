package pl.edu.agh.kis.panels;

import javax.swing.*;

/**
 * Created by Karl on 25.01.2017.
 */
public class HowManyRoundsLeftPanel extends JPanel {
    public HowManyRoundsLeftPanel(int howMany) {
        super();
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        if (howMany != 0) {
            textPane.setText("There are " + howMany + " rounds left");
        } else {
            textPane.setText("Last round!");
        }
        textPane.setEditable(false);
        add(textPane);
    }
}
