package pl.edu.agh.kis;

import pl.edu.agh.kis.Controller.QuestionController;
import pl.edu.agh.kis.Model.PlayerClientSide;
import pl.edu.agh.kis.Model.QuestionClientSide;
import pl.edu.agh.kis.Model.Reply;
import pl.edu.agh.kis.View.MainFrame;

import java.net.Socket;

/**
 * Created by Karl on 14.01.2017.
 */
public class PlayerClientSideWithGUI extends PlayerClientSide {
    MainFrame mainFrame;
    volatile boolean isAnswering = false;

    public PlayerClientSideWithGUI(Socket player, MainFrame mainFrame) {
        super(player);
        this.mainFrame = mainFrame;
    }



    //CZY MĄDRZE Z ARGUMENTEM
    @Override
    public boolean sendReply(Reply reply) { //MAYBE SEND BACK THAT EVERYTHING OK FROM SERVER??
        printWriter.println(reply.getPlayerChoice());
        printWriter.println(reply.getReplyTime());
        return true;
    }


    @Override
    public synchronized void play() {
        //for (int i = 0; i < 4; i++) {
        setAnswering(true);
        playRound();


        //}
    }



    public boolean isAnswering() {

        return isAnswering;
    }

    public void setAnswering(boolean answering) {
        isAnswering = answering;
    }

    public void playRound() {
        QuestionClientSide question = getQuestion();
        long time = System.nanoTime();
        System.out.println(question);
        QuestionController questionController = new QuestionController(question, time, this, mainFrame);
        System.out.println("before prepare");
        //questionController.getQuestionPanel().prepareAndShow();
        System.out.println("after");


        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isAnswering()) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        });
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        System.out.println(question);

        //sendReply(reply());
        System.out.println("Sended");
        //sendDecisionIsOver();
    }

}
