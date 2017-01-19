package pl.edu.agh.kis;

import pl.edu.agh.kis.game.GameSimpleRoundAbstract;
import pl.edu.agh.kis.game.GameSimpleRoundTranslations;
import pl.edu.agh.kis.game.GameSimpleRoundWithPhotos;
import pl.edu.agh.kis.player.PlayerServerSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Karl on 07.01.2017.
 */


public class Server {
    BlockingQueue<PlayerServerSide> players = new LinkedBlockingQueue<>();

    //Vector<PlayerServerSide> players = new Vector<>();
    private ServerSocket serverSocket;
    private int playersNumber;
    private LoggingToFile logger = new LoggingToFile("ServerLogs.txt");


    /**
     * @param portNumber
     */
    public Server(int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            logger.critical(e.getMessage());
            e.printStackTrace();
        }
    }
//JEsli gracz sie rozlaczy
    // progress bar ile rund
    //czy jpg czy nie wysyla zdjecie lub nie
    //config gry plik propierties klasa properties
    //gra rozpocznie się za 30 sekund jak dołączą wszyscy gracze
    //

    public static void main(String[] args) {
        if (args.length > 4) {
            Server server = new Server(Integer.parseInt(args[0]));
            int tmp = Integer.parseInt(args[3]);
            boolean cities = true;
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
            numberOfPlayer = 2;
            //cities = true;
            while (true) {

                Socket playerSocket = serverSocket.accept();
                PlayerServerSide player = new PlayerServerSide(playerSocket, ++playersNumber);
                addToPlayersList(player);
                if (players.size() == numberOfPlayer) {

                    PlayerServerSide playerServerSides[] = new PlayerServerSide[numberOfPlayer];

                    for (int i = 0; i < numberOfPlayer; i++) {

                        playerServerSides[i] = players.poll();
                    }

                    GameSimpleRoundAbstract game;
                    if (cities) {
                        game = new GameSimpleRoundWithPhotos(maximalRespondTime, playerServerSides);

                    } else {
                        game = new GameSimpleRoundTranslations(maximalRespondTime, playerServerSides);
                    }
                    game.play();
                    serverSocket.close();
                    break;
                }
            }

        } catch (IOException e) {
            logger.critical(e.getMessage());
            e.printStackTrace();
        }
    }

}
