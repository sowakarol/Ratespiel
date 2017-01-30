package pl.edu.agh.kis.messages.server;

import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Karl on 26.01.2017.
 * message informing about end of a game
 */
public class EndOfGameMessage extends MessageAbstract {
    private int hasWon;
    private int points;
    private int howManyDraws;

    public EndOfGameMessage(OutputStream out, boolean hasWon, int howManyDraws, int points) {
        super(out);
        message = (byte) ServerMessages.END.ordinal();
        if (hasWon) {
            this.hasWon = 1;
        } else {
            this.hasWon = 0;
        }
        this.points = points;
        this.howManyDraws = howManyDraws;
    }

    @Override
    public void send() {
        try {
            out.write(message);
            out.write(hasWon);
            out.write(howManyDraws);
            out.write(points);

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
