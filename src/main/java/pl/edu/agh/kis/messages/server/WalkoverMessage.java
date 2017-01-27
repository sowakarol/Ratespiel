package pl.edu.agh.kis.messages.server;

import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Karl on 27.01.2017.
 */
public class WalkoverMessage extends MessageAbstract {
    private int points;

    public WalkoverMessage(OutputStream out, int points) {
        super(out);
        message = (byte) ServerMessages.WALKOVER.ordinal();
        this.points = points;
    }

    @Override
    public void send() {
        try {
            out.write(message);
            out.write(points);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
