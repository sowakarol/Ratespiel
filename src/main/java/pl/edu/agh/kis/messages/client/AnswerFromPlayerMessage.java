package pl.edu.agh.kis.messages.client;

import pl.edu.agh.kis.Model.Reply;
import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by Karl on 22.01.2017.
 * Message with answer chosen by a player
 */
public class AnswerFromPlayerMessage extends MessageAbstract {
    private Reply reply;

    public AnswerFromPlayerMessage(OutputStream out, Reply reply) {
        super(out);
        this.reply = reply;
        message = (byte) PlayerMessages.ANSWER.ordinal();

    }

    @Override
    public void send() {
        try {
            out.write(message);
            out.flush();
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(out, "UTF-8"), true);
            printWriter.println(reply.getPlayerChoice());
            printWriter.println(reply.getReplyTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
