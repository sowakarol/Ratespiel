package pl.edu.agh.kis.messages.server;

import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Karl on 20.01.2017.
 * Class representing a server's message which informs a client about how many players have to connect to start a game
 */
public class WaitMessage extends MessageAbstract {
    int numberOfRequiredPlayers;

    public WaitMessage(OutputStream out, int numberOfRequiredPlayers) {
        super(out);
        message = (byte) ServerMessages.WAIT.ordinal();
        this.numberOfRequiredPlayers = numberOfRequiredPlayers;
    }

    @Override
    public void send() {
        try {
            out.write(numberOfRequiredPlayers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
