package pl.edu.agh.kis.messages;

import java.io.OutputStream;

/**
 * Created by Karl on 20.01.2017.
 * Abstract class representing message
 */
public abstract class MessageAbstract {
    /**
     * byte informing which type of data it's going to be sent
     */
    protected byte message;
    /**
     * variable accepting output bytes and sending them out
     */
    protected OutputStream out;

    /**
     * @param out initializing OutputStream, without it a class couldn't send information.
     */
    public MessageAbstract(OutputStream out) {
        this.out = out;
    }

    /**
     * getter of OutputStream variable
     *
     * @return OutputStream reference
     */
    public OutputStream getOut() {
        return out;
    }

    /**
     * send all data with OutputStream
     */
    public abstract void send();


}
