package pl.edu.agh.kis;

/**
 * Created by Karl on 06.01.2017.
 * Server-side Reply with every information to determinate a winner
 */
public abstract class Answer implements AnswerInterface {
    //checks time somehow??


    Reply reply;

    Answer(Reply reply) {
        this.reply = reply;
    }

    public boolean isTrue(QuestionServerSide question) {
        return question.IsTrue(this.reply.playerChoice);
    }

}
