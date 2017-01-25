package pl.edu.agh.kis.messages.server;

import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Karl on 20.01.2017.
 */
public class UnexpectedEndMessage extends MessageAbstract {

    public UnexpectedEndMessage(OutputStream out) {
        super(out);
        message = (byte) ServerMessages.UNEXPECTED_END.ordinal();
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
