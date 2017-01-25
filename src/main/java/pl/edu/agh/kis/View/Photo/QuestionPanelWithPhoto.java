package pl.edu.agh.kis.View.Photo;

import pl.edu.agh.kis.Controller.Photo.QuestionControllerWithPhoto;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.Model.question.QuestionClientSideAbstract;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.View.QuestionPanelAbstract;

import javax.swing.*;

/**
 * Created by Karl on 15.01.2017.
 */
public class QuestionPanelWithPhoto extends QuestionPanelAbstract {


    public QuestionPanelWithPhoto(QuestionClientSideAbstract question, MainFrame mainFrame, QuestionControllerWithPhoto questionController) {
        super(question, mainFrame, questionController);
        prepareAndShow();
    }


    public void prepareAndShow() {
        QuestionClientSideWithPhoto questionClientSideWithPhoto = (QuestionClientSideWithPhoto) question;
        JLabel questionLabel = new JLabel(new ImageIcon(questionClientSideWithPhoto.getImage()));


        add(questionLabel);

        JPanel answersButtonsPanel = new JPanel();
        answersButtonsPanel.setLayout(new BoxLayout(answersButtonsPanel, BoxLayout.Y_AXIS));


        addAnswersAndPrepare();


    }
}
