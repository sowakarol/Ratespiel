package pl.edu.agh.kis.server;

import pl.edu.agh.kis.messages.MessageAbstract;
import pl.edu.agh.kis.ratespiel.PlayerAbstract;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Karl on 22.01.2017.
 */
public class ServerSidePlayer extends PlayerAbstract implements ServerSidePlayerInterface {
    private String gameType;
    private int playersNumber;
    private int waitingTimeForNewGame;
    private int roundsNumber;
    private int maximalRespondTime;
    private int id;
    private int points;

    public ServerSidePlayer(Socket playerSocket, String gameType, int playersNumber,
                            int waitingTimeForNewGame, int roundsNumber, int maximalRespondTime, int id) {
        super(playerSocket);
        this.gameType = gameType;
        this.playersNumber = playersNumber;
        this.waitingTimeForNewGame = waitingTimeForNewGame;
        this.roundsNumber = roundsNumber;
        this.maximalRespondTime = maximalRespondTime;
        this.id = id;
        points = 0;
    }

    public int getId() {
        return id;
    }

    public void addPoints(int i) {
        points += i;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public MessageAbstract getMessage() {
        return null;
    }

    @Override
    public void closeConnection() {
        try {
            this.playerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void play() {


    }
}
