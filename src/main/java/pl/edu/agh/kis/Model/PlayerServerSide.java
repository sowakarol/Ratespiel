package pl.edu.agh.kis.Model;

import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by Karl on 07.01.2017.
 */
public class PlayerServerSide extends PlayerAbstract implements PlayerServerSideInterface {
    private int id;


    public PlayerServerSide(Socket player, int id) {
        super(player);
        this.id = id;

    }

    public int getId() {
        return id;
    }

    @Override
    public boolean sendQuestion(QuestionClientSide question) {
        printWriter.println(question.getToTranslate());
        for (int i = 0; i < 4; i++) {
            printWriter.println(question.getAnswers().get(i));
        }
        return true;
    }

    @Override
    public boolean sendQuestion(QuestionClientSideWithPhoto question) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(question.getImage(), "jpg", byteArrayOutputStream);

            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            outputStream.write(size);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
            System.out.println("Flushed: " + System.currentTimeMillis());

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 4; i++) {
            printWriter.println(question.getAnswers().get(i));
        }
        return true;
    }


    @Override
    public Answer answer() {
        return new Answer(getReply(), id);
    }

    @Override
    public Reply getReply() {
        try {
            String playerChoice = bufferedReader.readLine();
            long replyTime = Long.parseLong(bufferedReader.readLine());
            return new Reply(playerChoice, replyTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean quit() {
        int b = 1;
        try {
            b = inputStream.read();
            //closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (b == 1) {
            closeConnection();
            return true;
        } else {
            return false;
        }
    }


}
