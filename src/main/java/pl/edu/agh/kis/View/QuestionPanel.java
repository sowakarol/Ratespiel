package pl.edu.agh.kis.View;

import pl.edu.agh.kis.Model.QuestionClientSide;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Karl on 14.01.2017.
 */
public class QuestionPanel extends JPanel {
    QuestionClientSide question;

    QuestionPanel(QuestionClientSide question) {
        super();
        this.question = question;
    }

    public static void main(String[] args) throws InterruptedException {
        Vector<String> vector = new Vector<>();
        vector.add("aaa");
        vector.add("aba");
        vector.add("aca");
        vector.add("ada");


        QuestionClientSide questionClientSide = new QuestionClientSide(vector, "siemano");
        //MainFrame mainFrame = new MainFrame();
        //mainFrame.setDefault();
        //Thread.sleep(10000);
        // mainFrame.add()


        //JFrame frame = new JFrame();
        //QuestionPanel questionPanel = new QuestionPanel(questionClientSide);
        //questionPanel.prepareAndShow();
        //frame.add(questionPanel);
        //frame.pack();
        //frame.setVisible(true);

    }

    public void prepareAndShow() {
        JPanel questionString = new JPanel(new BorderLayout());
        JTextPane textPane = new JTextPane();

        textPane.setContentType("text/html");
        textPane.setText(question.getToTranslate());

        textPane.setEditable(false);
        questionString.add(textPane);

        add(questionString);

        JPanel answersButtonsPanel = new JPanel();
        answersButtonsPanel.setLayout(new BoxLayout(answersButtonsPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < 4; i++) {
            answersButtonsPanel.add(new JButton(question.getAnswers().get(i)));
            answersButtonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        add(answersButtonsPanel);
        setVisible(true);

    }

}
