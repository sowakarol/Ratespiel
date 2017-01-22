package pl.edu.agh.kis.server;

import pl.edu.agh.kis.messages.MessageAbstract;
import pl.edu.agh.kis.messages.server.ServerMessages;

/**
 * Created by Karl on 20.01.2017.
 */
public interface ServerSidePlayerInterface {
    void sendMessage(ServerMessages serverMessage);

    MessageAbstract getMessage();

    void closeConnection();
}
