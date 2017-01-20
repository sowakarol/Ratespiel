package pl.edu.agh.kis.messages;

import java.io.OutputStream;

/**
 * Created by Karl on 20.01.2017.
 */
public abstract class MessageAbstract {
    protected byte message;
    protected OutputStream out;


    public MessageAbstract(OutputStream out) {
        this.out = out;
    }

    public abstract void send();
}
