package pl.edu.agh.kis.messages.server;

import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Karl on 26.01.2017.
 */
public class GoodbyeIfDisconnectedMessage extends MessageAbstract {
    public GoodbyeIfDisconnectedMessage(OutputStream out) {
        super(out);
        message = (byte) ServerMessages.GOODBYE_IF_DISCONNECTED.ordinal();
    }

    @Override
    public void send() {
        try {
            out.write(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
