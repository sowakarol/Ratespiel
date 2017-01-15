package pl.edu.agh.kis.Controller;

import pl.edu.agh.kis.Model.Reply;
import pl.edu.agh.kis.PlayerClientSideWithGUI;
import pl.edu.agh.kis.View.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Karl on 15.01.2017.
 */
public class QuestionControllerAbstract implements ActionListener {
    private PlayerClientSideWithGUI player;
    private long deliveredTime;
    // private QuestionPanel questionPanel;
    private MainFrame mainFrame;
    //private QuestionClientSide question;

    public QuestionControllerAbstract(long deliveredTime, PlayerClientSideWithGUI player, MainFrame mainFrame) {
        this.player = player;
        this.deliveredTime = deliveredTime;
        this.mainFrame = mainFrame;
        //questionPanel = new QuestionPanel(question, mainFrame, this);

    }

    //public QuestionPanel getQuestionPanel() {
    //return questionPanel;
    //}

    @Override
    public void actionPerformed(ActionEvent e) {
        String chosenAnswer = e.getActionCommand();
        long time = System.nanoTime();
        Reply reply = new Reply(chosenAnswer, time - deliveredTime);
        player.sendReply(reply);
        player.setAnswering(false);
        //player.quit(true);
        //player.closeConnection();
        //WAIT
    }
}
