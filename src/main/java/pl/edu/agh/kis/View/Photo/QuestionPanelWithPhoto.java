package pl.edu.agh.kis.View.Photo;

import pl.edu.agh.kis.Controller.Photo.QuestionControllerWithPhoto;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.Model.question.QuestionClientSideAbstract;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.View.QuestionPanelAbstract;

import javax.swing.*;

/**
 * Created by Karl on 15.01.2017.
 * Panel of question with photo inside it
 */
public class QuestionPanelWithPhoto extends QuestionPanelAbstract {


    public QuestionPanelWithPhoto(QuestionClientSideAbstract question, MainFrame mainFrame,
                                  QuestionControllerWithPhoto questionController, int howManyLoopsLeft) {
        super(question, mainFrame, questionController, howManyLoopsLeft);
        prepareAndShow();
    }


    /**
     * method preparing frame with this panel
     */
    public final void prepareAndShow() {
        QuestionClientSideWithPhoto questionClientSideWithPhoto = (QuestionClientSideWithPhoto) question;
        JLabel questionLabel = new JLabel(new ImageIcon(questionClientSideWithPhoto.getImage()));


        add(questionLabel);

        JPanel answersButtonsPanel = new JPanel();
        answersButtonsPanel.setLayout(new BoxLayout(answersButtonsPanel, BoxLayout.Y_AXIS));


        addAnswersAndPrepare();
    }
}
