package pl.edu.agh.kis.Controller.Photo;

import pl.edu.agh.kis.Controller.QuestionControllerAbstract;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.View.Photo.QuestionPanelWithPhoto;
import pl.edu.agh.kis.client.ClientSidePlayer;

/**
 * Created by Karl on 15.01.2017.
 * Class representing QuestionController with a photo questions
 */
public class QuestionControllerWithPhoto extends QuestionControllerAbstract {
    /**
     * represented question to a GUI Client side
     */
    private QuestionClientSideWithPhoto question;
    /**
     * question Panel with Photo in which player will see question
     */
    private QuestionPanelWithPhoto questionPanel;


    /**
     * @param question         question which will be represented in GUI
     * @param deliveredTime    time in which question was get to client
     * @param player           answering player
     * @param mainFrame        answering player's mainFrame
     * @param howManyLoopsLeft number representing Rounds left
     */
    public QuestionControllerWithPhoto(QuestionClientSideWithPhoto question, long deliveredTime,
                                       ClientSidePlayer player, MainFrame mainFrame, int howManyLoopsLeft) {
        super(deliveredTime, player, mainFrame, howManyLoopsLeft);
        this.question = question;
        questionPanel = new QuestionPanelWithPhoto(question, mainFrame, this, howManyLoopsLeft);
    }
}
