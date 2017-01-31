package pl.edu.agh.kis.client;

import pl.edu.agh.kis.Controller.MainController;
import pl.edu.agh.kis.Controller.Photo.QuestionControllerWithPhoto;
import pl.edu.agh.kis.Controller.QuestionController;
import pl.edu.agh.kis.Controller.QuestionControllerAbstract;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.Model.question.QuestionClientSide;
import pl.edu.agh.kis.answer.Reply;
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
 * Class representing a player on a client side. Contains all methods necessary to play. It defines a game and is connected to GUI.
 *
 */
public class ClientSidePlayer extends PlayerAbstract { // CHANGE NAME
    /**
     * a MainController of a player
     */
    private MainController main;
    /**
     * There are two main types in this game - a city type, and words to translate type
     * this string is representing in which one a player is playing. It's sent from server.
     * Ratespiel can be expended so that gameType will be needed here.
     */
    private String gameType;//for future functionalities - printing to a player in which version of game he plays
    /**
     * Variable representing the number of players. Sent by a server.
     * When Ratespiel will be expanded to a version that flashes usernames with their scores after game ended it might be useful.
     */
    private int playersNumber;
    /**
     * Variable representing time in seconds that the server waits after players needed for a game's start.
     */
    private int waitingTimeForNewGame;
    /**
     * Variable representing a number of rounds that a game consist of
     */
    private int roundsNumber;
    /**
     * Variable representing time in seconds that a client has to answer for every question
     */
    private int maximalRespondTime;
    /**
     * Variable representing a boolean value is a player in chosen moment answering for a question
     */
    private boolean isAnswering = false;
    /**
     * variable used to clear a main GUI frame
     */
    private Container container;
    /**
     * variable representing a boolean value is a player connected to a server
     */
    private boolean isConnected;

    /**
     * The only constructor of ClientSidePlayer class
     *
     * @param playerSocket socket maintaining a connection with server
     * @param main         MainController communicating and updating GUI of a client
     */
    public ClientSidePlayer(Socket playerSocket, MainController main) {
        super(playerSocket);
        this.main = main;
        isConnected = true;
    }

    /**
     * method cleaning a main frame
     * @return dimension of a main frame before cleaning
     */
    private Dimension clearMainFrameAndGetDimension() {
        Dimension ret = main.getMainFrame().getSize();
        container = main.getMainFrame().getContentPane();
        container.removeAll();
        return ret;

    }

    /**
     * @return a value of a boolean isAnswering variable
     */
    public boolean isAnswering() {
        return isAnswering;
    }

    /**
     * method setting isAnswering boolean value
     * @param answering new boolean value of a isAnswering value
     */
    public void setAnswering(boolean answering) {
        isAnswering = answering;
    }

    /**
     * method sending a hello request to a server, getting from server and initializing
     * playersNumber, roundsNumber, gameType, maximalRespondTime and waitingTimeForNewGame variables
     */
    public void getHelloFromServer() {
        sendMessage(new HelloFromClientMessage(outputStream));
        byte b[] = new byte[6];
        try {
            //reading from server
            for (int i = 0; i < b.length; i++) {
                b[i] = (byte) inputStream.read();
            }
            //initializing variables
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

    /**
     * @return true - if player waited for a game or false if player disconnected
     */
    public boolean waitForGame() {
        byte b[] = new byte[2];
        b[0] = -1;
        try {
            b[0] = (byte) inputStream.read();
            //while game not started
            while (b[0] != ServerMessages.START_GAME.ordinal()) {
                //wait message changing the state of waiting game - some player disconnected or connected

                if (b[0] == ServerMessages.WAIT.ordinal()) {
                    int requiredPlayers = inputStream.read();
                    int connectedPlayers = inputStream.read();

                    //updating a waiting panel
                    Dimension d = clearMainFrameAndGetDimension();
                    main.getMainFrame().getContentPane().add(new WaitForOtherPlayersPanel(requiredPlayers, connectedPlayers));
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

        //setting up a GetReadyPanel
        /*Dimension d = clearMainFrameAndGetDimension();
        main.getMainFrame().getContentPane().add(new GetReadyPanel(waitingTimeForNewGame, playersNumber - 1));
        main.getMainFrame().pack();
        main.getMainFrame().validate();
        main.getMainFrame().repaint();
        main.getMainFrame().setSize(d);
        main.getMainFrame().setVisible(true);
        System.out.println("BE READY " + waitingTimeForNewGame + " seconds for a game!");*/
        return true;
    }

    /**
     * method getting a question QuestionClientSide from server
     * @return a QuestionClientSide class for current loop
     */
    public QuestionClientSide getQuestion() {
        String toTranslate = "";
        ArrayList<String> answers = new ArrayList<String>();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            toTranslate = br.readLine();
            for (int i = 0; i < 4; i++) {
                answers.add(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new QuestionClientSide(answers, toTranslate);
    }

    /**
     * @return isConnected variable's value
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * @param connected a new value for isConnected variable
     */
    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    /**
     * @return QuestionClientSideWithPhoto object from a server for a current loop
     */
    public QuestionClientSideWithPhoto getQuestionWithPhoto() {
        ArrayList<String> answers = new ArrayList<String>();

        byte[] buffer;
        ObjectInputStream input;
        BufferedImage image = null;
        try {
            input = new ObjectInputStream(inputStream);

            for (int i = 0; i < 4; i++) {
                answers.add(input.readUTF());
            }
            buffer = (byte[]) input.readObject();
            image = ImageIO.read(new ByteArrayInputStream(buffer));
            //image = ImageIO.read((File) input.readObject());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }


        return new QuestionClientSideWithPhoto(answers, image);

    }


    /**
     * main method doing all connected player's actions - from sending hello request, playing round, sending answers to displaying win/lose/draw at the end
     */
    public void play() {
        getHelloFromServer();
        if (waitForGame() && isConnected()) {
            boolean waitingIsOver = false;
            byte x;
            while (waitingIsOver) {
                try {
                    x = (byte) inputStream.read();
                    if (x != ServerMessages.WAIT_FOR_X_SECONDS.ordinal()) {
                        break;
                    }
                    x = (byte) inputStream.read();
                    Dimension d = clearMainFrameAndGetDimension();
                    main.getMainFrame().getContentPane().add(new GetReadyPanel(x, playersNumber - 1));
                    main.getMainFrame().pack();
                    main.getMainFrame().validate();
                    main.getMainFrame().repaint();
                    main.getMainFrame().setSize(d);
                    main.getMainFrame().setVisible(true);
                    System.out.println("BE READY " + waitingTimeForNewGame + " seconds for a game!");

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


                if (isConnected())
                    sendMessage(new ReadyPlayerMessage(outputStream));

                for (int i = 0; i < roundsNumber; i++) {
                    if (isConnected()) {
                        playRound(i + 1);
                    }
                }
                if (isConnected()) {
                    endGame();
                }


        }
    }


    /**
     * method which takes all necessity actions to player that allows him to play a round
     * @param currentLoop number of a current loop - helps displaying how many loops left till the end of a game
     */
    public void playRound(int currentLoop) {
        byte[] bytes = new byte[1];
        bytes[0] = -1;
        try {
            bytes[0] = (byte) inputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //beginning of counting time from client side
        long startedTime = System.nanoTime();

        isAnswering = true;
        if (bytes[0] == ServerMessages.QUESTION.ordinal()) {
            QuestionClientSide q = getQuestion();
            QuestionController questionController = new QuestionController(q, startedTime, this,
                    main.getMainFrame(), roundsNumber - currentLoop);

            bytes[0] = -1;
            sendAnswer(questionController, bytes);


        } else if (bytes[0] == ServerMessages.QUESTION_WITH_PHOTO.ordinal()) {
            QuestionClientSideWithPhoto q = getQuestionWithPhoto();
            QuestionControllerWithPhoto questionController = new QuestionControllerWithPhoto(q, startedTime, this,
                    main.getMainFrame(), roundsNumber - currentLoop);
            //questionController.listenToTimeout();
            bytes[0] = -1;
            sendAnswer(questionController, bytes);

        } else if (bytes[0] == ServerMessages.WALKOVER.ordinal()) {
            winByWalkover();
        }
    }


    /**
     * @param questionController Question Controller that has all necessary information about answer
     * @param bytes byte array which will be used to read all information from server
     */
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
                        //if player not answered get him the highest answerTime
                        if (!questionController.isClicked()) {
                            questionController.setClicked(true);
                            questionController.setAnswerTime(Long.MAX_VALUE);
                        }
                        if (!isConnected()) {
                            break;
                        }

                        Reply reply = new Reply(questionController.getChosenAnswer(), questionController.getAnswerTime());
                        System.out.println("sending answer");
                        new AnswerFromPlayerMessage(outputStream, reply).send();
                        setAnswering(false);
                        break;

                        // in case of Walkover message from server, create a walkover panel
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


    /**
     * method which creates walkover panel
     */
    private void winByWalkover() {
        Dimension d = clearMainFrameAndGetDimension();
        main.getMainFrame().getContentPane().add(new WalkoverPanel());
        main.getMainFrame().pack();
        main.getMainFrame().validate();
        main.getMainFrame().repaint();
        main.getMainFrame().setSize(d);
        main.getMainFrame().setVisible(true);

    }


    /**
     * method getting information from server about player result of a game
     */
    private void endGame() {
        byte b = -1;
        while (true) {
            try {
                b = (byte) inputStream.read();
                if (b == ServerMessages.END.ordinal()) {
                    //read information from server about result
                    int hasWon = inputStream.read();
                    int howManyDraws = inputStream.read();
                    int points = inputStream.read();
                    byte negative = (byte) inputStream.read();
                    Dimension d = clearMainFrameAndGetDimension();
                    if (negative == 1) {
                        points = -points;
                    }
                    //adding Panels depending on a result
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

                    //adding pointsPanel
                    main.getMainFrame().getContentPane().add(new PointsPanel(points));
                    main.getMainFrame().pack();
                    main.getMainFrame().validate();
                    main.getMainFrame().repaint();
                    main.getMainFrame().setSize(d);
                    main.getMainFrame().setVisible(true);
                    break;
                    // in case of Walkover message from server, create a walkover panel
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
