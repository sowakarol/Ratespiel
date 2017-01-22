package pl.edu.agh.kis.messages.client;

import pl.edu.agh.kis.Model.Answer;
import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by Karl on 22.01.2017.
 */
public class AnswerFromPlayerMessage extends MessageAbstract {
    PrintWriter printWriter;
    Answer answer;

    public AnswerFromPlayerMessage(OutputStream out, Answer answer) {
        super(out);
        this.answer = answer;
        message = (byte) PlayerMessages.ANSWER.ordinal();

    }

    @Override
    public void send() {
        try {
            out.write(message);
            printWriter = new PrintWriter(out, true);

            printWriter.println(answer.getReply().getPlayerChoice());
            printWriter.println(answer.getReply().getReplyTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
