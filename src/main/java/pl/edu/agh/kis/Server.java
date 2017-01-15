package pl.edu.agh.kis;

import pl.edu.agh.kis.Model.PlayerServerSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by Karl on 07.01.2017.
 */
public class Server implements Runnable {
    Vector<PlayerServerSide> players = new Vector<>();    //Vector<PlayerServerSide> players = new Vector<>();
    private ServerSocket serverSocket;
    private int playersNumber;
    private LoggingToFile logger = new LoggingToFile("ServerLogs.txt");

    public Server(int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            logger.critical(e.getMessage());
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Server server = new Server(8888);
        server.listenAndPreparePlayer();
    }

    @Override
    public void run() {
        listenAndPreparePlayer();
    }

    private synchronized boolean addToPlayersList(PlayerServerSide player) {
        return players.add(player);
    }

    public void listenAndPreparePlayer() {
        try {
            while (true) {
                Socket playerSocket = serverSocket.accept();
                PlayerServerSide player = new PlayerServerSide(playerSocket, ++playersNumber);
                addToPlayersList(player);
                if (players.size() == 2) {
                    GameSimpleRoundTranslations game = new GameSimpleRoundTranslations(5, players.get(0), players.get(1));
                    game.play();
                    serverSocket.close();
                }

            }

        } catch (IOException e) {
            logger.critical(e.getMessage());
            e.printStackTrace();
        }
    }

}
