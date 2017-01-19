package pl.edu.agh.kis.Controller;

import pl.edu.agh.kis.Model.question.QuestionClientSide;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.View.QuestionPanel;
import pl.edu.agh.kis.player.PlayerClientSideWithGUI;


/**
 * Created by Karl on 14.01.2017.
 */
public class QuestionController extends QuestionControllerAbstract {
    private QuestionPanel questionPanel;
    private QuestionClientSide question;

    public QuestionController(QuestionClientSide question, long deliveredTime, PlayerClientSideWithGUI player, MainFrame mainFrame) {
        super(deliveredTime, player, mainFrame);
        this.question = question;
        questionPanel = new QuestionPanel(question, mainFrame, this);
    }

    public QuestionPanel getQuestionPanel() {
        return questionPanel;
    }

}
