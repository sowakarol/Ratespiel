package pl.edu.agh.kis.View;

import pl.edu.agh.kis.Controller.QuestionController;
import pl.edu.agh.kis.Model.question.QuestionClientSide;
import pl.edu.agh.kis.Model.question.QuestionClientSideAbstract;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Karl on 14.01.2017.
 * Panel displaying normal question without photo
 */
public class QuestionPanel extends QuestionPanelAbstract {


    /**
     * constructor initializing variables and using prepareAndShow() method
     *
     * @param question
     * @param mainFrame
     * @param questionController
     * @param howManyLoopsLeft
     */
    public QuestionPanel(QuestionClientSideAbstract question, MainFrame mainFrame,
                         QuestionController questionController, int howManyLoopsLeft) {
        super(question, mainFrame, questionController, howManyLoopsLeft);
        prepareAndShow();

    }


    /**
     * method adding panel of a word to be translated or question to be asked
     */
    public final void prepareAndShow() {
        QuestionClientSide questionClientSide = (QuestionClientSide) question;
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
