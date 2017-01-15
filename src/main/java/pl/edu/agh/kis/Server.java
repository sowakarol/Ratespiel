package pl.edu.agh.kis;

import pl.edu.agh.kis.Model.PlayerServerSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by Karl on 07.01.2017.
 */
public class Server {
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
        if (args.length > 4) {
            Server server = new Server(Integer.parseInt(args[0]));
            int tmp = Integer.parseInt(args[3]);
            boolean cities = false;
            if (tmp == 1) {
                cities = true;
            }
            server.listenAndPreparePlayer(Integer.parseInt(args[1]), Integer.parseInt(args[2]), cities);
        }
    }



    private synchronized boolean addToPlayersList(PlayerServerSide player) {
        return players.add(player);
    }

    public void listenAndPreparePlayer(int numberOfPlayer, int maximalRespondTime, boolean cities) {
        try {
            while (true) {

                Socket playerSocket = serverSocket.accept();
                PlayerServerSide player = new PlayerServerSide(playerSocket, ++playersNumber);
                addToPlayersList(player);
                if (players.size() == numberOfPlayer) {
                    if (cities) {
                        GameSimpleRoundWithPhotos game = new GameSimpleRoundWithPhotos(maximalRespondTime, players.get(0), players.get(1));
                        game.play();
                        serverSocket.close();
                        break;
                    } else {
                        GameSimpleRoundTranslations game = new GameSimpleRoundTranslations(maximalRespondTime, players.get(0), players.get(1));
                        game.play();
                        serverSocket.close();
                        break;
                    }
                }

            }

        } catch (IOException e) {
            logger.critical(e.getMessage());
            e.printStackTrace();
        }
    }


}
