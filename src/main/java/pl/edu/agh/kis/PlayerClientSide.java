package pl.edu.agh.kis;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by Karl on 07.01.2017.
 */
public class PlayerClientSide extends PlayerAbstract implements PlayerClientSideInterface {
    PlayerClientSide(Socket player) {
        super(player);
    }

    @Override
    public QuestionClientSide getQuestion() {
        try {
            String toTranslate = bufferedReader.readLine();
            Vector<String> answers = new Vector<>(4);
            for (int i = 0; i < 4; i++) {
                answers.add(bufferedReader.readLine());
            }
            return new QuestionClientSide(answers, toTranslate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean sendReply(Reply reply) { //MAYBE SEND BACK THAT EVERYTHING OK FROM SERVER??
        printWriter.println(reply.getPlayerChoice());
        printWriter.println(reply.getReplyTime());
        return true;
    }


    //ASK IF quit CAN RETURN TRUE!!!!
    @Override
    public boolean quit(boolean playerChoice) {
        if (playerChoice) {
            try {
                outputStream.write(1);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                outputStream.write(0);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
