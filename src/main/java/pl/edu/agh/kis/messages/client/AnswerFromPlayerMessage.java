package pl.edu.agh.kis.messages.client;

import pl.edu.agh.kis.Model.Reply;
import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by Karl on 22.01.2017.
 */
public class AnswerFromPlayerMessage extends MessageAbstract {
    PrintWriter printWriter;
    Reply reply;

    public AnswerFromPlayerMessage(OutputStream out, Reply reply) {
        super(out);
        this.reply = reply;
        message = (byte) PlayerMessages.ANSWER.ordinal();

    }

    @Override
    public void send() {
        try {
            out.write(message);
            printWriter = new PrintWriter(out, true);

            printWriter.println(reply.getPlayerChoice());
            printWriter.println(reply.getReplyTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
