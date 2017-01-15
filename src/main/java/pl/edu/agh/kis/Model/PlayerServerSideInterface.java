package pl.edu.agh.kis.Model;

import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;

/**
 * Created by Karl on 07.01.2017.
 */
public interface PlayerServerSideInterface {
    boolean sendQuestion(QuestionClientSide question);

    boolean sendQuestion(QuestionClientSideWithPhoto question);

    Answer answer();

    Reply getReply();

    boolean quit();

    void closeConnection();

    void addPoints(int numberOfPoints);
}
