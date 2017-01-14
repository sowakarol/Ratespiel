package pl.edu.agh.kis.Controller;

import pl.edu.agh.kis.Model.QuestionClientSide;
import pl.edu.agh.kis.Model.Reply;
import pl.edu.agh.kis.PlayerClientSideWithGUI;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.View.QuestionPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Karl on 14.01.2017.
 */
public class QuestionController implements ActionListener {
    String chosenAnswer = null;
    PlayerClientSideWithGUI player;
    Reply reply;
    long deliveredTime;
    QuestionPanel questionPanel;
    MainFrame mainFrame;
    QuestionClientSide question;

    public QuestionController(QuestionClientSide question, long deliveredTime, PlayerClientSideWithGUI player, MainFrame mainFrame) {
        this.player = player;
        this.deliveredTime = deliveredTime;
        this.mainFrame = mainFrame;
        this.question = question;
        questionPanel = new QuestionPanel(question, mainFrame, this);

    }

    public QuestionPanel getQuestionPanel() {
        return questionPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chosenAnswer = e.getActionCommand();
        long time = System.nanoTime();
        reply = new Reply(chosenAnswer, time - deliveredTime);
        player.sendReply(reply);
        player.setAnswering(false);
        //player.quit(true);
        //player.closeConnection();
        //WAIT
    }
}
