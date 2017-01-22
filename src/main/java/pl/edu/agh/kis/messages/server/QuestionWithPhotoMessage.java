package pl.edu.agh.kis.messages.server;

import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.messages.MessageAbstract;

import javax.imageio.ImageIO;
import java.io.*;

/**
 * Created by Karl on 22.01.2017.
 */
public class QuestionWithPhotoMessage extends MessageAbstract {
    QuestionClientSideWithPhoto question;
    ByteArrayOutputStream baos;

    public QuestionWithPhotoMessage(OutputStream out, QuestionClientSideWithPhoto question) {
        super(out);
        this.question = question;
        message = (byte) ServerMessages.QUESTION_WITH_PHOTO.ordinal();
    }

    @Override
    public void send() {
        try {
            out.write(message);
            PrintWriter pw = new PrintWriter(out, true);
            for (int i = 0; i < question.getAnswers().size(); i++) {
                pw.println(question.getAnswers().get(i));
            }



            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            baos = new ByteArrayOutputStream();
            ImageIO.write(question.getImage(), "jpg", baos);
            baos.flush();
            System.out.println("Size of baos = " + baos.size());
            byte[] buffer = baos.toByteArray();
            baos.close();
            baos = null;
            objectOutputStream.writeObject(buffer);
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
