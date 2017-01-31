package pl.edu.agh.kis.ratespiel;

import pl.edu.agh.kis.messages.MessageAbstract;

/**
 * Created by Karl on 28.01.2017.
 * Player Interface from both server and client side
 */
public interface PlayerInterface {
    /**
     * @param message message which is going to be send
     */
    void sendMessage(MessageAbstract message);

    /**
     * closing connection to a server
     */
    void closeConnection();
}
