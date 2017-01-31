package pl.edu.agh.kis.answer;

/**
 * Created by Karl on 06.01.2017.
 * Reply of a player
 */
public class Reply {
    /**
     * string answer of player
     */
    protected String playerChoice;
    /**
     * time in which player replied
     */
    protected long replyTime;


    /**
     * @param playerChoice choice of a player
     * @param replyTime    time in which player replied
     */
    public Reply(String playerChoice, long replyTime) {
        this.playerChoice = playerChoice;
        this.replyTime = replyTime;
    }

    /**
     * @return player choice
     */
    public String getPlayerChoice() {
        return playerChoice;
    }

    /**
     * @return reply in which player replied
     */
    public long getReplyTime() {
        return replyTime;
    }
}
