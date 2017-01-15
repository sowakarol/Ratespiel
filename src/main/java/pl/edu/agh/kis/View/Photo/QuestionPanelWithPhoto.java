package pl.edu.agh.kis.View.Photo;

import pl.edu.agh.kis.Controller.Photo.QuestionControllerWithPhoto;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.Model.QuestionClientSide;
import pl.edu.agh.kis.Model.QuestionClientSideAbstract;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.View.QuestionPanelAbstract;

import javax.swing.*;
import java.util.Vector;

/**
 * Created by Karl on 15.01.2017.
 */
public class QuestionPanelWithPhoto extends QuestionPanelAbstract {


    public QuestionPanelWithPhoto(QuestionClientSideAbstract question, MainFrame mainFrame, QuestionControllerWithPhoto questionController) {
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


    }

    public void prepareAndShow() {
        QuestionClientSideWithPhoto questionClientSideWithPhoto = (QuestionClientSideWithPhoto) question;
        System.out.println(":O");
        JLabel questionLabel = new JLabel(new ImageIcon(questionClientSideWithPhoto.getImage()));


        add(questionLabel);

        JPanel answersButtonsPanel = new JPanel();
        answersButtonsPanel.setLayout(new BoxLayout(answersButtonsPanel, BoxLayout.Y_AXIS));


        addAnswersAndPrepare();


    }
}
