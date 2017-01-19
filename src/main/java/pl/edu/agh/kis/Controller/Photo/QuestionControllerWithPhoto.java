package pl.edu.agh.kis.Controller.Photo;

import pl.edu.agh.kis.Controller.QuestionControllerAbstract;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.View.Photo.QuestionPanelWithPhoto;
import pl.edu.agh.kis.player.PlayerClientSideWithGUIPhoto;

/**
 * Created by Karl on 15.01.2017.
 */
public class QuestionControllerWithPhoto extends QuestionControllerAbstract {
    QuestionClientSideWithPhoto question;
    private QuestionPanelWithPhoto questionPanel;


    public QuestionControllerWithPhoto(QuestionClientSideWithPhoto question, long deliveredTime, PlayerClientSideWithGUIPhoto player, MainFrame mainFrame) {
        super(deliveredTime, player, mainFrame);
        this.question = question;
        questionPanel = new QuestionPanelWithPhoto(question, mainFrame, this);


    }


}
