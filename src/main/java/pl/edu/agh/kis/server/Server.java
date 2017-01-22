package pl.edu.agh.kis.server;

import pl.edu.agh.kis.LoggingToFile;
import pl.edu.agh.kis.messages.server.HelloServerMessage;
import pl.edu.agh.kis.utils.RatespielGetPropertyValues;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Karl on 20.01.2017.
 * Class starting a game, getting values from config.properties, checking connection and listening for players
 */
public class Server {
    private LoggingToFile logger = new LoggingToFile("ServerLogs.txt");
    private String gameType;
    private int playersNumber;
    private String hostname;
    private int waitingTimeForNewGame;
    private ServerSocket serverSocket;
    private int roundsNumber;
    private int maximalRespondTime;
    //private ArrayList<ServerSidePlayerInterface> players;

    public Server() {
        gameType = RatespielGetPropertyValues.getGameType();
        playersNumber = RatespielGetPropertyValues.getPlayersNumber();
        hostname = RatespielGetPropertyValues.getHostname();
        waitingTimeForNewGame = RatespielGetPropertyValues.getWaitingTimeForNewGame();
        roundsNumber = RatespielGetPropertyValues.getroundsNumber();
        maximalRespondTime = RatespielGetPropertyValues.getMaximalRespondTime();

        try {
            serverSocket = new ServerSocket(RatespielGetPropertyValues.getPortNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {


    }

    /*private void listenAndPrepareGame(int numberOfConnectedPlayers) {
        try {
            while (true) {

                Socket playerSocket = serverSocket.accept();
                PlayerServerSide player = new PlayerServerSide(playerSocket, ++numberOfConnectedPlayers);


                players.add(player);
                if (players.size() == playersNumber) {

                    PlayerServerSide playerServerSides[] = new PlayerServerSide[numberOfPlayer];

                    for (int i = 0; i < playersNumber; i++) {

                        playerServerSides[i] = players.poll();
                    }

                    GameSimpleRoundAbstract game;
                    /*if (cities) {
                        game = new GameSimpleRoundWithPhotos(maximalRespondTime, playerServerSides);

                    } else {
                        game = new GameSimpleRoundTranslations(maximalRespondTime, playerServerSides);
                    }


                    game.play();
                    serverSocket.close();
                    break;
                }
                }

            } catch(IOException e){
                logger.critical(e.getMessage());
                e.printStackTrace();
                //SOMEONE HAS DISCONNECTED

            }
        }


    }*/


    private void handleNewPlayer(Socket playerSocket, int gameTypeRepresentation) {
        try {
            OutputStream out = playerSocket.getOutputStream();
            InputStream in = playerSocket.getInputStream();
            byte b[] = new byte[1];
            b[0] = 1;
            in.read(b, 0, b.length);
            if (b[0] == 0) {
                new HelloServerMessage(out, playersNumber, roundsNumber, gameTypeRepresentation,
                        maximalRespondTime, waitingTimeForNewGame);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}