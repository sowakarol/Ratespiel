package pl.edu.agh.kis.Model;

/**
 * Created by Karl on 07.01.2017.
 */
public abstract class AnswerAbstract implements AnswerInterface {//jakiś mechanizm/klasa sprawdza czy odpowiedz jest poprawna
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
