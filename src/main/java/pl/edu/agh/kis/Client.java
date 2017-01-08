package pl.edu.agh.kis;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Karl on 08.01.2017.
 */
public class Client {
    private int portNumber;
    private String hostname;
    private Socket playerSocket;

    Client(int portNumber, String hostname) {
        this.portNumber = portNumber;
        this.hostname = hostname;
        try {
            playerSocket = new Socket(hostname, portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client(8888, "localhost");
        PlayerClientSide player = new PlayerClientSide(client.getPlayerSocket());
        player.play();
    }

    public Socket getPlayerSocket() {
        return playerSocket;
    }


}
