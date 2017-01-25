package pl.edu.agh.kis.client;

import pl.edu.agh.kis.Controller.MainController;
import pl.edu.agh.kis.Controller.Photo.QuestionControllerWithPhoto;
import pl.edu.agh.kis.Controller.QuestionController;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.Model.Reply;
import pl.edu.agh.kis.Model.question.QuestionClientSide;
import pl.edu.agh.kis.messages.client.AnswerFromPlayerMessage;
import pl.edu.agh.kis.messages.client.DisconnectPlayerMessage;
import pl.edu.agh.kis.messages.client.HelloFromClientMessage;
import pl.edu.agh.kis.messages.client.ReadyPlayerMessage;
import pl.edu.agh.kis.ratespiel.PlayerAbstract;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by Karl on 22.01.2017.
 *
 *
 */
public class ClientSidePlayer extends PlayerAbstract { // CHANGE NAME
    private MainController main;
    private String gameType;
    private int playersNumber;
    private int waitingTimeForNewGame;
    private int roundsNumber;
    private int maximalRespondTime;
    private boolean isAnswering = false;

    public ClientSidePlayer(Socket playerSocket, MainController main) {
        super(playerSocket);
        this.main = main;

    }

    public boolean isAnswering() {
        return isAnswering;
    }

    public void setAnswering(boolean answering) {
        isAnswering = answering;
    }

    public void getHelloFromServer() {
        sendMessage(new HelloFromClientMessage(outputStream));
        byte b[] = new byte[6];
        try {
            for (int i = 0; i < b.length; i++) {
                System.out.println(i);
                b[i] = (byte) inputStream.read();
            }
            if (b[0] == 0) {
                playersNumber = b[1];
                roundsNumber = b[2];
                switch (b[3]) {
                    case 0:
                        gameType = "translation";
                        break;
                    case 1:
                        gameType = "cities";
                        break;
                    default:
                        gameType = "translation";
                }
                maximalRespondTime = b[4];
                waitingTimeForNewGame = b[5];
                for (byte a : b) {
                    System.out.println(a);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean waitForGame() {
        byte b[] = new byte[2];
        b[0] = -1;
        try {
            b[0] = (byte) inputStream.read();
            while (b[0] != 2) {
                if (b[0] == 1) {
                    int requiredPlayers = inputStream.read();
                    System.out.println(requiredPlayers + " more!! WAIT");
                }
                b[0] = (byte) inputStream.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
            sendMessage(new DisconnectPlayerMessage(outputStream));
            return false;
        }
        System.out.println("BE READY " + waitingTimeForNewGame + " seconds for a game!");
        return true;
    }

    public QuestionClientSide getQuestion() {
        String toTranslate = "";
        Vector<String> answers = new Vector<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        try {
            toTranslate = br.readLine();
            for (int i = 0; i < 4; i++) {
                answers.add(br.readLine());
                System.out.println(answers.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new QuestionClientSide(answers, toTranslate);
    }

    public QuestionClientSideWithPhoto getQuestionWithPhoto() {

        Vector<String> answers = new Vector<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        try {
            for (int i = 0; i < 4; i++) {
                answers.add(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buffer;
        ObjectInputStream input = null;
        BufferedImage image = null;
        try {
            input = new ObjectInputStream(inputStream);
            buffer = (byte[]) input.readObject();
            image = ImageIO.read(new ByteArrayInputStream(buffer));


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new QuestionClientSideWithPhoto(answers, image);

    }

    //TODO
    //JUST DO IT!!
    public void play() {
        getHelloFromServer();
        if (waitForGame()) {
            try {
                Thread.sleep(waitingTimeForNewGame * 1000);
                sendMessage(new ReadyPlayerMessage(outputStream));

                for (int i = 0; i < roundsNumber; i++) {
                    //sendMessage(new ReadyPlayerMessage(outputStream));

                    playRound();
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }



    public void playRound() {
        byte[] bytes = new byte[1];
        bytes[0] = -1;
        try {
            bytes[0] = (byte) inputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = System.nanoTime();
        //System.out.println(question);
        //QuestionController questionController = new QuestionController(question, time, this, mainFrame);
        isAnswering = true;
        if (bytes[0] == 3) {
            QuestionClientSide q = getQuestion();
            System.out.println("1");
            QuestionController questionController = new QuestionController(q, time, this, main.getMainFrame());
            System.out.println("2");
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
        thread.start();

*/

            bytes[0] = -1;
            System.out.println("3");
            while (true) {
                System.out.println("4");
                if (bytes[0] == 9) {
                    if (!questionController.isClicked()) {
                        questionController.setClicked(true);
                        questionController.setAnswerTime(Long.MAX_VALUE);
                    }
                    Reply reply = new Reply(questionController.getChosenAnswer(), Long.MAX_VALUE);
                    System.out.println("WYSYLAM");
                    new AnswerFromPlayerMessage(outputStream, reply).send();
                    setAnswering(false);
                    break;
                }
                try {
                    bytes[0] = (byte) inputStream.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //questionController.listenToTimeout();


        } else if (bytes[0] == 4) {
            QuestionClientSideWithPhoto q = getQuestionWithPhoto();
            QuestionControllerWithPhoto questionController = new QuestionControllerWithPhoto(q, time, this, main.getMainFrame());
            //questionController.listenToTimeout();
        }
    }
}
