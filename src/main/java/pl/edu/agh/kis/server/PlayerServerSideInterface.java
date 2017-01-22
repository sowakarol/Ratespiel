package pl.edu.agh.kis.server;

import pl.edu.agh.kis.Model.Answer;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.Model.Reply;
import pl.edu.agh.kis.Model.question.QuestionClientSide;

/**
 * Created by Karl on 07.01.2017.
 */
public interface PlayerServerSideInterface {
    boolean sendQuestion(QuestionClientSide question);

    boolean sendQuestion(QuestionClientSideWithPhoto question);

    Answer answer();

    Reply getReply();

    boolean sendResult(byte b);

    void closeConnection();

    void addPoints(int numberOfPoints);
}
