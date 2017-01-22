package pl.edu.agh.kis.server;

import pl.edu.agh.kis.messages.MessageAbstract;

/**
 * Created by Karl on 20.01.2017.
 */
public interface ServerSidePlayerInterface {
    void sendMessage(MessageAbstract message);

    MessageAbstract getMessage();

    void closeConnection();

    void play();
}
