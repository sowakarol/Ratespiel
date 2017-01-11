package pl.edu.agh.kis.Model;

/**
 * Created by Karl on 08.01.2017.
 */
public abstract class ReplyAbstract {
    //measure time somehow??

    protected String playerChoice;
    protected long replyTime;


    public ReplyAbstract(String playerChoice, long replyTime) {
        this.playerChoice = playerChoice;
        this.replyTime = replyTime;
    }

    public String getPlayerChoice() {
        return playerChoice;
    }

    public long getReplyTime() {
        return replyTime;
    }
}
