package pl.edu.agh.kis.client;

import pl.edu.agh.kis.Controller.MainController;
import pl.edu.agh.kis.Controller.Photo.QuestionControllerWithPhoto;
import pl.edu.agh.kis.Controller.QuestionController;
import pl.edu.agh.kis.Controller.QuestionControllerAbstract;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.Model.Reply;
import pl.edu.agh.kis.Model.question.QuestionClientSide;
import pl.edu.agh.kis.messages.client.AnswerFromPlayerMessage;
import pl.edu.agh.kis.messages.client.DisconnectPlayerMessage;
import pl.edu.agh.kis.messages.client.HelloFromClientMessage;
import pl.edu.agh.kis.messages.client.ReadyPlayerMessage;
import pl.edu.agh.kis.messages.server.ServerMessages;
import pl.edu.agh.kis.panels.*;
import pl.edu.agh.kis.ratespiel.PlayerAbstract;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

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
    private Container container;
    private boolean isConnected;

    public ClientSidePlayer(Socket playerSocket, MainController main) {
        super(playerSocket);
        this.main = main;
        isConnected = true;


    }

    private Dimension clearMainFrameAndGetDimension() {
        Dimension ret = main.getMainFrame().getSize();
        container = main.getMainFrame().getContentPane();
        container.removeAll();
        return ret;

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
            while (b[0] != ServerMessages.START_GAME.ordinal()) {
                if (b[0] == ServerMessages.WAIT.ordinal()) {
                    int requiredPlayers = inputStream.read();


                    Dimension d = clearMainFrameAndGetDimension();

                    main.getMainFrame().getContentPane().add(new WaitForOtherPlayersPanel(requiredPlayers));
                    main.getMainFrame().pack();
                    main.getMainFrame().validate();
                    main.getMainFrame().repaint();
                    main.getMainFrame().setSize(d);
                    main.getMainFrame().setVisible(true);
                    System.out.println(requiredPlayers + " more!! WAIT");
                } else if (b[0] == ServerMessages.GOODBYE_IF_DISCONNECTED.ordinal()) {
                    System.out.println("Disconnected");
                    break;
                }
                b[0] = (byte) inputStream.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
            sendMessage(new DisconnectPlayerMessage(outputStream));
            return false;
        }

        Dimension d = clearMainFrameAndGetDimension();

        main.getMainFrame().getContentPane().add(new GetReadyPanel(waitingTimeForNewGame));
        main.getMainFrame().pack();
        main.getMainFrame().validate();
        main.getMainFrame().repaint();
        main.getMainFrame().setSize(d);
        main.getMainFrame().setVisible(true);
        System.out.println(waitingTimeForNewGame + " more!! WAIT");


        System.out.println("BE READY " + waitingTimeForNewGame + " seconds for a game!");
        return true;
    }

    public QuestionClientSide getQuestion() {
        String toTranslate = "";
        ArrayList<String> answers = new ArrayList<>();
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

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public QuestionClientSideWithPhoto getQuestionWithPhoto() {

        ArrayList<String> answers = new ArrayList<>();

        byte[] buffer;
        ObjectInputStream input;
        BufferedImage image = null;
        try {
            input = new ObjectInputStream(inputStream);

            for (int i = 0; i < 4; i++) {
                answers.add(input.readUTF());
                System.out.println(answers.get(i));
            }
            buffer = (byte[]) input.readObject();
            image = ImageIO.read(new ByteArrayInputStream(buffer));
            //image = ImageIO.read((File) input.readObject());

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
        if (waitForGame() && isConnected()) {
            try {
                Thread.sleep(waitingTimeForNewGame * 1000);
                if (isConnected())
                    sendMessage(new ReadyPlayerMessage(outputStream));

                for (int i = 0; i < roundsNumber; i++) {
                    //sendMessage(new ReadyPlayerMessage(outputStream));
                    if (isConnected()) {
                        playRound(i + 1);
                    }
                }
                if (isConnected()) {
                    endGame();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void playRound(int currentLoop) {
        byte[] bytes = new byte[1];
        bytes[0] = -1;
        try {
            bytes[0] = (byte) inputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long Startedtime = System.nanoTime();
        //System.out.println(question);
        //QuestionController questionController = new QuestionController(question, time, this, mainFrame);
        isAnswering = true;
        if (bytes[0] == ServerMessages.QUESTION.ordinal()) {
            QuestionClientSide q = getQuestion();
            System.out.println("1");
            QuestionController questionController = new QuestionController(q, Startedtime, this,
                    main.getMainFrame(), roundsNumber - currentLoop);
            System.out.println("2");

            bytes[0] = -1;
            System.out.println("3");
            sendAnswer(questionController, bytes);


        } else if (bytes[0] == ServerMessages.QUESTION_WITH_PHOTO.ordinal()) {
            QuestionClientSideWithPhoto q = getQuestionWithPhoto();
            QuestionControllerWithPhoto questionController = new QuestionControllerWithPhoto(q, Startedtime, this,
                    main.getMainFrame(), roundsNumber - currentLoop);
            //questionController.listenToTimeout();
            bytes[0] = -1;
            System.out.println("3");
            sendAnswer(questionController, bytes);

        } else if (bytes[0] == ServerMessages.WALKOVER.ordinal()) {
            winByWalkover();
        }
    }


    private void sendAnswer(QuestionControllerAbstract questionController, byte[] bytes) {
        while (true) {
            try {
                Thread.sleep(200);
                try {
                    if (isConnected() && inputStream.available() > 0) {
                        bytes[0] = (byte) inputStream.read();

                    } else if (!isConnected()) {
                        break;
                    }
                    if (bytes[0] == ServerMessages.GET_ANSWER.ordinal()) {
                        if (!questionController.isClicked()) {
                            questionController.setClicked(true);
                            questionController.setAnswerTime(Long.MAX_VALUE);
                        }
                        if (!isConnected()) {
                            break;
                        }
                        Reply reply = new Reply(questionController.getChosenAnswer(), questionController.getAnswerTime());
                        System.out.println("WYSYLAM");
                        new AnswerFromPlayerMessage(outputStream, reply).send();
                        setAnswering(false);
                        break;
                    } else if (bytes[0] == ServerMessages.WALKOVER.ordinal()) {
                        winByWalkover();
                        break;
                    }

                } catch (IOException e1) {
                    e1.printStackTrace();
                    break;
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                break;
            }


        }

    }


    private void winByWalkover() {
        Dimension d = clearMainFrameAndGetDimension();
        main.getMainFrame().getContentPane().add(new WalkoverPanel());
        main.getMainFrame().pack();
        main.getMainFrame().validate();
        main.getMainFrame().repaint();
        main.getMainFrame().setSize(d);
        main.getMainFrame().setVisible(true);

    }


    //check every while!!
    private void endGame() {
        byte b = -1;
        while (true) {
            try {
                b = (byte) inputStream.read();
                if (b == ServerMessages.END.ordinal()) {
                    int hasWon = inputStream.read();
                    int howManyDraws = inputStream.read();
                    int points = inputStream.read();
                    Dimension d = clearMainFrameAndGetDimension();

                    if (hasWon == 1 && howManyDraws == 0) {
                        main.getMainFrame().getContentPane().add(new WinPanel());
                        main.getMainFrame().pack();

                    } else if (hasWon == 1 && howManyDraws > 0) {
                        main.getMainFrame().getContentPane().add(new DrawPanel(howManyDraws));
                        main.getMainFrame().pack();

                    } else {
                        main.getMainFrame().getContentPane().add(new FailPanel());
                        main.getMainFrame().pack();

                    }
                    main.getMainFrame().getContentPane().add(new PointsPanel(points));
                    main.getMainFrame().pack();
                    main.getMainFrame().validate();
                    main.getMainFrame().repaint();
                    main.getMainFrame().setSize(d);
                    main.getMainFrame().setVisible(true);
                    break;
                } else if (b == ServerMessages.WALKOVER.ordinal()) {
                    winByWalkover();
                    break;
                }


            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        setConnected(false);
    }
}
