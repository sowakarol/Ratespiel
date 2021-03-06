package pl.edu.agh.kis.messages.server;

import pl.edu.agh.kis.Model.question.QuestionClientSide;
import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by Karl on 22.01.2017.
 * message with question, send method randomizing answers
 */
public class QuestionMessage extends MessageAbstract {
    private QuestionClientSide question;


    public QuestionMessage(OutputStream out, QuestionClientSide question) {
        super(out);
        message = (byte) ServerMessages.QUESTION.ordinal();
        this.question = question;
    }

    @Override
    public void send() {
        try {
            out.write(message);
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(out, "UTF-8"), true);
            printWriter.println(question.getToTranslate());
            question.randomizeAnswers();

            for (int i = 0; i < question.getAnswers().size(); i++) {
                System.out.println(this.toString() + "  " + question.getAnswers().get(i));
                printWriter.println(question.getAnswers().get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
