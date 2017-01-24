package pl.edu.agh.kis.messages.server;

import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Karl on 24.01.2017.
 */
public class GetAnswerMessage extends MessageAbstract {


    public GetAnswerMessage(OutputStream out) {
        super(out);
        message = (byte) ServerMessages.GET_ANSWER.ordinal();


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
