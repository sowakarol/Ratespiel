package pl.edu.agh.kis.Controller;

import pl.edu.agh.kis.Model.question.QuestionClientSide;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.View.QuestionPanel;
import pl.edu.agh.kis.client.ClientSidePlayer;


/**
 * Created by Karl on 14.01.2017.
 *  Class representing QuestionController with questions without photo
 */
public class QuestionController extends QuestionControllerAbstract {
    /**
     * question Panel with Photo in which player will see question
     */
    private QuestionPanel questionPanel;
    /**
     * represented question to a GUI Client side
     */
    private QuestionClientSide question;

    /**
     * @param question         question which will be represented in GUI
     * @param deliveredTime    time in which question was get to client
     * @param player           answering player
     * @param mainFrame        answering player's mainFrame
     * @param howManyLoopsLeft number representing Rounds left
     */
    public QuestionController(QuestionClientSide question, long deliveredTime, ClientSidePlayer player, MainFrame mainFrame, int howManyLoopsLeft) {
        super(deliveredTime, player, mainFrame, howManyLoopsLeft);
        this.question = question;
        questionPanel = new QuestionPanel(question, mainFrame, this, howManyLoopsLeft);
    }

    /**
     * getter of QuestionPanel
     * @return variable questionPanel
     */
    public QuestionPanel getQuestionPanel() {
        return questionPanel;
    }

}
