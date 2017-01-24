package pl.edu.agh.kis.messages.client;

import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Karl on 20.01.2017.
 */
public class HelloFromClientMessage extends MessageAbstract {


    public HelloFromClientMessage(OutputStream out) {
        super(out);
        message = (byte) PlayerMessages.HELLO_CLIENT.ordinal();
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
