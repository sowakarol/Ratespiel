package pl.edu.agh.kis;

/**
 * Created by Karl on 06.01.2017.
 */
public interface PlayerServerSide {
    boolean sendQuestion(QuestionServerSide);

    Answer answer(Reply reply);

    Reply getReply();
}
