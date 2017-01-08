package pl.edu.agh.kis;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Karl on 07.01.2017.
 */
public class PlayerServerSide extends PlayerAbstract implements PlayerServerSideInterface {
    private int id;


    PlayerServerSide(Socket player, int id) {
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
        return false;
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
