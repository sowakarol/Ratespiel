package pl.edu.agh.kis.Controller.Photo;

import pl.edu.agh.kis.Controller.QuestionControllerAbstract;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.PlayerClientSideWithGUI;
import pl.edu.agh.kis.View.MainFrame;

/**
 * Created by Karl on 15.01.2017.
 */
public class QuestionControllerWithPhoto extends QuestionControllerAbstract {
    QuestionClientSideWithPhoto question;


    public QuestionControllerWithPhoto(QuestionClientSideWithPhoto question, long deliveredTime, PlayerClientSideWithGUI player, MainFrame mainFrame) {
        super(deliveredTime, player, mainFrame);
        this.question = question;
    }
}
