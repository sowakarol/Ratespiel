package pl.edu.agh.kis.panels;

import javax.swing.*;

/**
 * Created by Karl on 30.01.2017.
 * Answer panel after sent
 */
public class AnswerSentPanel extends JPanel {
    public AnswerSentPanel() {
        super();
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("Answer Sent, wait for a next question!");
        textPane.setEditable(false);
        add(textPane);
    }

}
