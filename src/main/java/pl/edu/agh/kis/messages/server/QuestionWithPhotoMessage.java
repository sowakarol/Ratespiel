package pl.edu.agh.kis.messages.server;

import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.messages.MessageAbstract;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by Karl on 22.01.2017.
 * message with question with photo, send method randomizing answers
 */
public class QuestionWithPhotoMessage extends MessageAbstract {
    private QuestionClientSideWithPhoto question;

    public QuestionWithPhotoMessage(OutputStream out, QuestionClientSideWithPhoto question) {
        super(out);
        this.question = question;
        message = (byte) ServerMessages.QUESTION_WITH_PHOTO.ordinal();
    }

    @Override
    public void send() {
        try {
            out.write(message);
            question.randomizeAnswers();

            ObjectOutputStream outputStream = new ObjectOutputStream(out);
            for (int i = 0; i < question.getAnswers().size(); i++) {
                outputStream.writeUTF(question.getAnswers().get(i));
            }


            //ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(question.getImage(), "jpg", baos);
            baos.flush();
            //System.out.println("Size of baos = " + baos.size());
            byte[] buffer = baos.toByteArray();
            baos.close();
            baos = null;
            outputStream.writeObject(buffer);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
