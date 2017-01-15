package pl.edu.agh.kis;

import pl.edu.agh.kis.Controller.Photo.QuestionControllerWithPhoto;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.View.MainFrame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Vector;

/**
 * Created by Karl on 15.01.2017.
 */
public class PlayerClientSideWithGUIPhoto extends PlayerClientSideWithGUIAbstract {
    public PlayerClientSideWithGUIPhoto(Socket player, MainFrame mainFrame) {
        super(player, mainFrame);
    }


    public QuestionClientSideWithPhoto getQuestionWithPhoto() {
        try {
            byte[] sizeAr = new byte[4];
            inputStream.read(sizeAr);
            int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

            byte[] imageAr = new byte[size];
            inputStream.read(imageAr);

            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
            Vector<String> answers = new Vector<>(4);
            for (int i = 0; i < 4; i++) {
                answers.add(bufferedReader.readLine());
            }
            return new QuestionClientSideWithPhoto(answers, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void playRound() {
        QuestionClientSideWithPhoto question = getQuestionWithPhoto();
        long time = System.nanoTime();
        System.out.println(question);
        QuestionControllerWithPhoto questionController = new QuestionControllerWithPhoto(question, time, this, mainFrame);
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
