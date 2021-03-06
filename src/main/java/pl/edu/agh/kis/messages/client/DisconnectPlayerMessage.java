package pl.edu.agh.kis.messages.client;

import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Karl on 22.01.2017.
 * message with information about disconnecting from player
 */
public class DisconnectPlayerMessage extends MessageAbstract {
    public DisconnectPlayerMessage(OutputStream out) {
        super(out);
        message = (byte) PlayerMessages.DISCONNECT.ordinal();
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
