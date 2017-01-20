package pl.edu.agh.kis.messages.server;

import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Karl on 20.01.2017.
 * class representing a Hello Message from Server including informing a Client about number of players, kind
 * of game
 */
public class HelloServerMessage extends MessageAbstract {
    private int numberOfPlayers;
    private int numberOfRounds;
    private int gameType;
    private int maximalRespondTime;
    private int waitingTimeForNewGame;


    public HelloServerMessage(OutputStream out, int numberOfPlayers, int numberOfRounds, int gameType, int maximalRespondTime, int waitingTimeForNewGame) {
        super(out);
        message = (byte) ServerMessages.HELLO_SERVER.ordinal();
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfRounds = numberOfRounds;
        this.gameType = gameType;
        this.maximalRespondTime = maximalRespondTime;
        this.waitingTimeForNewGame = waitingTimeForNewGame;
    }


    @Override
    public void send() {
        try {
            out.write(message);

            out.write(numberOfPlayers);

            out.write(numberOfRounds);

            out.write(gameType);

            out.write(maximalRespondTime);

            out.write(waitingTimeForNewGame);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
