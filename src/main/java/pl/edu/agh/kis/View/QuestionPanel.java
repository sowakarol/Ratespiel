package pl.edu.agh.kis.View;

import pl.edu.agh.kis.Controller.QuestionController;
import pl.edu.agh.kis.Model.QuestionClientSide;
import pl.edu.agh.kis.Model.QuestionClientSideAbstract;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Karl on 14.01.2017.
 */
public class QuestionPanel extends QuestionPanelAbstract {


    public QuestionPanel(QuestionClientSideAbstract question, MainFrame mainFrame, QuestionController questionController) {
        super(question, mainFrame, questionController);
        prepareAndShow();

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
