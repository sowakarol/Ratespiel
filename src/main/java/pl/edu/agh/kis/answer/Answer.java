package pl.edu.agh.kis.answer;

/**
 * Created by Karl on 07.01.2017.
 * Class representing Answer Server Side
 */


public class Answer {
    /**
     * reply from client
     */
    protected Reply reply;
    /**
     * playerID of answering player
     */
    protected int playerID;

    /**
     * @param reply    reply given by player
     * @param playerID id given by player
     */
    public Answer(Reply reply, int playerID) {
        this.reply = reply;
        this.playerID = playerID;
    }

    /**
     * @return ID of answering player
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * @return reply of answering player
     */
    public Reply getReply() {

        return reply;
    }}
