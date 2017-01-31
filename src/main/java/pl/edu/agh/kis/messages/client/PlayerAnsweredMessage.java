package pl.edu.agh.kis.messages.client;

import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Karl on 31.01.2017.
 */
public class PlayerAnsweredMessage extends MessageAbstract {
    /**
     * @param out initializing OutputStream, without it a class couldn't send information.
     */
    public PlayerAnsweredMessage(OutputStream out) {
        super(out);
        message = (byte) PlayerMessages.PLAYER_ANSWERED_MESSAGE.ordinal();
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
