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


    public ServerSidePlayer(Socket playerSocket, String gameType, int playersNumber,
                            int waitingTimeForNewGame, int roundsNumber, int maximalRespondTime) {
        super(playerSocket);
        this.gameType = gameType;
        this.playersNumber = playersNumber;
        this.waitingTimeForNewGame = waitingTimeForNewGame;
        this.roundsNumber = roundsNumber;
        this.maximalRespondTime = maximalRespondTime;
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
