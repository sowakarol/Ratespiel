package pl.edu.agh.kis;

/**
 * Created by Karl on 07.01.2017.
 */
public interface PlayerServerSideInterface {
    boolean sendQuestion(QuestionServerSide question);

    Answer answer(Reply reply);

    Reply getReply();
}
