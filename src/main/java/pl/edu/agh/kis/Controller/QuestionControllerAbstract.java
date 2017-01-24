package pl.edu.agh.kis.Controller;

import pl.edu.agh.kis.Model.Reply;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.client.ClientSidePlayer;
import pl.edu.agh.kis.messages.client.AnswerFromPlayerMessage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Karl on 15.01.2017.
 */
public class QuestionControllerAbstract implements ActionListener {
    boolean clicked = false;
    private ClientSidePlayer player;
    private long deliveredTime;
    private Reply reply;
    private long answerTime;
    // private QuestionPanel questionPanel;
    private MainFrame mainFrame;
    //private QuestionClientSide question;

    public QuestionControllerAbstract(long deliveredTime, ClientSidePlayer player, MainFrame mainFrame) {
        this.player = player;
        this.deliveredTime = deliveredTime;
        this.mainFrame = mainFrame;
        listenToTimeout();
        //questionPanel = new QuestionPanel(question, mainFrame, this);

    }

    //public QuestionPanel getQuestionPanel() {
    //return questionPanel;
    //}
    public final void listenToTimeout() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = new byte[1];
                bytes[0] = -1;
                while (true) {
                    if (bytes[0] == 9) {
                        //Reply reply = new Reply(null, Long.MAX_VALUE);
                        new AnswerFromPlayerMessage(player.getOutputStream(), reply).send();
                        break;
                    }
                    try {
                        bytes[0] = (byte) player.getInputStream().read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        });
        thread.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!clicked) {
            String chosenAnswer = e.getActionCommand();
            answerTime = System.nanoTime();
            reply = new Reply(chosenAnswer, answerTime - deliveredTime);
            //new AnswerFromPlayerMessage(player.getOutputStream(), reply).send();
            //player.sendReply(reply);
            //player.setAnswering(false);
            //player.quit(true);
            //player.closeConnection();
            //WAIT
            clicked = true;
        }
    }
}
