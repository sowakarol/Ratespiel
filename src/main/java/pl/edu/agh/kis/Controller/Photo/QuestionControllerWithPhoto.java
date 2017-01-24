package pl.edu.agh.kis.Controller.Photo;

import pl.edu.agh.kis.Controller.QuestionControllerAbstract;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.View.Photo.QuestionPanelWithPhoto;
import pl.edu.agh.kis.client.ClientSidePlayer;

/**
 * Created by Karl on 15.01.2017.
 */
public class QuestionControllerWithPhoto extends QuestionControllerAbstract {
    QuestionClientSideWithPhoto question;
    private QuestionPanelWithPhoto questionPanel;


    public QuestionControllerWithPhoto(QuestionClientSideWithPhoto question, long deliveredTime, ClientSidePlayer player, MainFrame mainFrame) {
        super(deliveredTime, player, mainFrame);
        this.question = question;
        questionPanel = new QuestionPanelWithPhoto(question, mainFrame, this);


    }


}
