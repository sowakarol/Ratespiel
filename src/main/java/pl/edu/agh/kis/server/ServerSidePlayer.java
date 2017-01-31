package pl.edu.agh.kis.server;

import pl.edu.agh.kis.ratespiel.PlayerAbstract;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Karl on 22.01.2017.
 * Player from server side
 */
public class ServerSidePlayer extends PlayerAbstract {
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

    /**
     * @param i number of points which are going to be incremented
     */
    public void addPoints(int i) {
        points += i;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public void closeConnection() {
        try {
            this.playerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
