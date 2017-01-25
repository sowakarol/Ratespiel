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
    private boolean clicked = false;
    private ClientSidePlayer player;
    private long deliveredTime;
    private Reply reply;
    private long answerTime;
    private String chosenAnswer;
    // private QuestionPanel questionPanel;
    private MainFrame mainFrame;

    public QuestionControllerAbstract(long deliveredTime, ClientSidePlayer player, MainFrame mainFrame) {
        this.player = player;
        this.deliveredTime = deliveredTime;
        this.mainFrame = mainFrame;
        //listenToTimeout();
        //questionPanel = new QuestionPanel(question, mainFrame, this);

    }

    public long getDeliveredTime() {
        return deliveredTime;
    }

    public void setDeliveredTime(long deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    public long getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(long answerTime) {
        this.answerTime = answerTime;
    }

    public String getChosenAnswer() {
        return chosenAnswer;
    }

    public void setChosenAnswer(String chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }

    public boolean isClicked() {

        return clicked;
    }
    //private QuestionClientSide question;

    public void setClicked(boolean clicked) {

        this.clicked = clicked;
    }

    //public QuestionPanel getQuestionPanel() {
    //return questionPanel;
    //}
    public void listenToTimeout() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = new byte[1];
                bytes[0] = -1;
                while (bytes[0] != 9) {
                    if (bytes[0] == 9) {
                        if (!clicked) {
                            clicked = true;
                            answerTime = Long.MAX_VALUE;
                        }
                        reply = new Reply(chosenAnswer, Long.MAX_VALUE);
                        System.out.println("WYSYLAM");
                        new AnswerFromPlayerMessage(player.getOutputStream(), reply).send();
                        player.setAnswering(false);
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
            chosenAnswer = e.getActionCommand();
            answerTime = System.nanoTime();
            //reply = new Reply(chosenAnswer, answerTime - deliveredTime);
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
