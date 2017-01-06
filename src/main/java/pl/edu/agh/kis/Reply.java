package pl.edu.agh.kis;

/**
 * Created by Karl on 06.01.2017.
 */
public abstract class Reply {
    //measure time somehow??

    String playerChoice;
    long replyTime;

    public Reply(String playerChoice, long replyTime) {
        this.playerChoice = playerChoice;
        this.replyTime = replyTime;
    }
}
