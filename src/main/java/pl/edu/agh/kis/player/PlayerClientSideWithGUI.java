package pl.edu.agh.kis.player;

import pl.edu.agh.kis.Controller.QuestionController;
import pl.edu.agh.kis.Model.question.QuestionClientSide;
import pl.edu.agh.kis.View.MainFrame;

import java.net.Socket;

/**
 * Created by Karl on 14.01.2017.
 */
public class PlayerClientSideWithGUI extends PlayerClientSideWithGUIAbstract {

    public PlayerClientSideWithGUI(Socket player, MainFrame mainFrame) {
        super(player, mainFrame);
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