package pl.edu.agh.kis;

/**
 * Created by Karl on 07.01.2017.
 */
public class AnswerAbstract implements AnswerInterface {
    //checks time somehow??
    protected Reply reply;
    protected int playerID;

    AnswerAbstract(Reply reply, int playerID) {
        this.reply = reply;
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public Reply getReply() {

        return reply;
    }

    public boolean isTrue(QuestionServerSide question) {
        return question.IsTrue(this.reply.getPlayerChoice());
    }
}