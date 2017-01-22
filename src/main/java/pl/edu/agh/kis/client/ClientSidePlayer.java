package pl.edu.agh.kis.client;

import pl.edu.agh.kis.Controller.MainController;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.Model.question.QuestionClientSide;
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

    public ClientSidePlayer(Socket playerSocket) {
        super(playerSocket);

    }

    public void getHelloFromServer() {
        sendMessage(new HelloFromClientMessage(outputStream));
        byte b[] = new byte[6];
        try {
            inputStream.read(b);
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

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean waitForGame() {
        byte b[] = new byte[1];
        b[0] = -1;
        try {
            inputStream.read(b);
            while (b[0] != 2) {
                if (b[0] == 1) {
                    int requiredPlayers = b[1];
                    System.out.println(requiredPlayers + " more!! WAIT");
                }
                inputStream.read(b);
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        QuestionClientSide questionClientSide = new QuestionClientSide(answers, toTranslate);

        return questionClientSide;
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


    public void play() {
        getHelloFromServer();
        if (waitForGame()) {
            try {
                Thread.sleep(waitingTimeForNewGame * 1000);
                sendMessage(new ReadyPlayerMessage(outputStream));
                byte[] bytes = new byte[1];
                if (bytes[0] == 3) {
                    QuestionClientSide q = getQuestion();


                } else if (bytes[0] == 4) {
                    QuestionClientSideWithPhoto q = getQuestionWithPhoto();
                }



            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


    }


}
