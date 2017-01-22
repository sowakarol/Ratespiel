package pl.edu.agh.kis.messages.client;

import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Karl on 22.01.2017.
 */
public class ReadyPlayerMessage extends MessageAbstract {
    public ReadyPlayerMessage(OutputStream out) {
        super(out);
        message = (byte) PlayerMessages.READY.ordinal();
    }

    @Override
    public void send() {
        try {
            out.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
