package pl.edu.agh.kis.server;

import pl.edu.agh.kis.LoggingToFile;
import pl.edu.agh.kis.messages.server.HelloServerMessage;
import pl.edu.agh.kis.messages.server.StartGameMessage;
import pl.edu.agh.kis.messages.server.WaitMessage;
import pl.edu.agh.kis.ratespiel.GameBasicVersion;
import pl.edu.agh.kis.utils.RatespielGetPropertyValues;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
    private ArrayList<ServerSidePlayer> players;

    public Server() {
        gameType = RatespielGetPropertyValues.getGameType();
        playersNumber = RatespielGetPropertyValues.getPlayersNumber();
        hostname = RatespielGetPropertyValues.getHostname();
        waitingTimeForNewGame = RatespielGetPropertyValues.getWaitingTimeForNewGame();
        roundsNumber = RatespielGetPropertyValues.getroundsNumber();
        maximalRespondTime = RatespielGetPropertyValues.getMaximalRespondTime();
        players = new ArrayList<>(playersNumber);

        try {
            serverSocket = new ServerSocket(RatespielGetPropertyValues.getPortNumber(), 0, InetAddress.getByName(hostname));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startGame();
    }

    public void startGame() {
        int numberOfConnectedPlayers = 0;
        try {
            while (true) {
                Socket playerSocket = serverSocket.accept();
                System.out.println("asd");
                // SERVERSIDEPLAYER!!   PlayerServerSide player = new PlayerServerSide(playerSocket, ++numberOfConnectedPlayers);
                ServerSidePlayer player = new ServerSidePlayer(playerSocket, gameType, playersNumber, waitingTimeForNewGame, roundsNumber,
                        maximalRespondTime, ++numberOfConnectedPlayers);
                int gameTypeRepresentation;
                switch (gameType) {
                    case "translation":
                        gameTypeRepresentation = 0;
                        break;
                    case "cities":
                        gameTypeRepresentation = 1;
                        break;
                    default:
                        gameTypeRepresentation = 0;
                        break;
                }
                handleNewPlayer(playerSocket, gameTypeRepresentation);
                players.add(player);
                System.out.println("added");
                if (players.size() == playersNumber) {
                    for (ServerSidePlayer p : players) {
                        new StartGameMessage(p.getOutputStream()).send();
                    }

                    GameBasicVersion gameBasicVersion = new GameBasicVersion(players,
                            waitingTimeForNewGame, RatespielGetPropertyValues.getPath(), roundsNumber);
                    gameBasicVersion.play();
                    break;
                } else {
                    new WaitMessage(player.getOutputStream(), numberOfConnectedPlayers).send();
                }

            }
            //serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


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
            b[0] = (byte) in.read();
            if (b[0] == 0) {
                new HelloServerMessage(out, playersNumber, roundsNumber, gameTypeRepresentation,
                        maximalRespondTime, waitingTimeForNewGame).send();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}