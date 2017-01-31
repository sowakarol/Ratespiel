package pl.edu.agh.kis.messages.server;

import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Karl on 31.01.2017.
 */
public class WaitForXSecondsMessage extends MessageAbstract {
    /**
     * @param out initializing OutputStream, without it a class couldn't send information.
     */

    private int seconds;

    public WaitForXSecondsMessage(OutputStream out, int seconds) {
        super(out);
        message = (byte) ServerMessages.WAIT_FOR_X_SECONDS.ordinal();
        this.seconds = seconds;
    }

    @Override
    public void send() {
        try {
            out.write(message);
            out.write(seconds);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
