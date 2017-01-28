package pl.edu.agh.kis.ratespiel;

import pl.edu.agh.kis.messages.MessageAbstract;

/**
 * Created by Karl on 28.01.2017.
 */
public interface PlayerInterface {
    void sendMessage(MessageAbstract message);

    void closeConnection();
}
