package pl.edu.agh.kis.panels;

import javax.swing.*;

/**
 * Created by Karl on 25.01.2017.
 */
public class WaitForNextQuestionPanel extends JPanel {
    public WaitForNextQuestionPanel() {
        super();
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("Answer sent - wait for next Question");
        textPane.setEditable(false);
        add(textPane);
    }
}
