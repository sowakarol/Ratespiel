package pl.edu.agh.kis.View;

import pl.edu.agh.kis.Controller.QuestionController;
import pl.edu.agh.kis.Model.QuestionClientSide;
import pl.edu.agh.kis.Model.QuestionClientSideAbstract;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Karl on 14.01.2017.
 */
public class QuestionPanel extends QuestionPanelAbstract {


    public QuestionPanel(QuestionClientSideAbstract question, MainFrame mainFrame, QuestionController questionController) {
        super(question, mainFrame, questionController);
        prepareAndShow();

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
        QuestionClientSide questionClientSide = (QuestionClientSide) question;
        System.out.println(":O");
        JPanel questionString = new JPanel(new BorderLayout());
        JTextPane textPane = new JTextPane();

        textPane.setContentType("text/html");
        textPane.setText(questionClientSide.getToTranslate());

        textPane.setEditable(false);
        questionString.add(textPane);

        add(questionString);

        addAnswersAndPrepare();

    }

}
