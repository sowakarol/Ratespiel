package pl.edu.agh.kis.player;

import pl.edu.agh.kis.Model.Reply;
import pl.edu.agh.kis.Model.question.QuestionClientSide;

/**
 * Created by Karl on 07.01.2017.
 */
public interface PlayerClientSideInterface {
    QuestionClientSide getQuestion();

    boolean sendReply(Reply reply);

    boolean quit(boolean playerChoice);

    void play();

    void closeConnection();


    /**
     * @return true if a player won the game, or false
     */
    boolean getResult();


}
