package pl.edu.agh.kis.server;

import pl.edu.agh.kis.LoggingToFile;
import pl.edu.agh.kis.messages.client.PlayerMessages;
import pl.edu.agh.kis.messages.server.HelloServerMessage;
import pl.edu.agh.kis.messages.server.StartGameMessage;
import pl.edu.agh.kis.messages.server.WaitMessage;
import pl.edu.agh.kis.ratespiel.GameBasicVersion;
import pl.edu.agh.kis.utils.RatespielGetPropertyValues;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Karl on 20.01.2017.
 * Class starting a game, getting values from config.properties, checking connection and listening for players
 */
public class Server {
    boolean gameStarted;
    private LoggingToFile logger = new LoggingToFile("ServerLogs.txt");
    private String gameType;
    private int playersNumber;
    private String hostname;
    private int waitingTimeForNewGame;
    private ServerSocket serverSocket;
    private int roundsNumber;
    private int maximalRespondTime;
    private ArrayList<ServerSidePlayer> players;
    private int numberOfConnectedPlayers;

    public Server() {
        gameType = RatespielGetPropertyValues.getGameType();
        playersNumber = RatespielGetPropertyValues.getPlayersNumber();
        hostname = RatespielGetPropertyValues.getHostname();
        waitingTimeForNewGame = RatespielGetPropertyValues.getWaitingTimeForNewGame();
        roundsNumber = RatespielGetPropertyValues.getroundsNumber();
        maximalRespondTime = RatespielGetPropertyValues.getMaximalRespondTime();
        players = new ArrayList<ServerSidePlayer>(playersNumber);


        try {
            serverSocket = new ServerSocket(RatespielGetPropertyValues.getPortNumber(), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startGame();
    }

    public void startGame() {
        numberOfConnectedPlayers = 0;
        try {
            while (true) {
                Socket playerSocket = serverSocket.accept();
                // SERVERSIDEPLAYER!!   PlayerServerSide player = new PlayerServerSide(playerSocket, ++numberOfConnectedPlayers);
                final ServerSidePlayer player = new ServerSidePlayer(playerSocket, gameType, playersNumber, waitingTimeForNewGame, roundsNumber,
                        maximalRespondTime, ++numberOfConnectedPlayers);
                int gameTypeRepresentation;

                if (gameType.equals("translation")) {
                    gameTypeRepresentation = 0;
                } else if (gameType.equals("cities")) {
                    gameTypeRepresentation = 1;
                } else {
                    gameTypeRepresentation = 0;
                }

                //adding player
                handleNewPlayer(playerSocket, gameTypeRepresentation);
                players.add(player);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        checkForPlayerDisconnect(player);
                    }
                });
                thread.start();


                if (players.size() == playersNumber) {

                    gameStarted = true;

                    for (ServerSidePlayer p : players) {
                        new StartGameMessage(p.getOutputStream()).send();
                    }
                    gameStarted = true;
                    System.out.println("started");
                    GameBasicVersion gameBasicVersion = new GameBasicVersion(players,
                            maximalRespondTime, RatespielGetPropertyValues.getPath(), roundsNumber);
                    gameBasicVersion.play();
                    break;
                } else {
                    for (ServerSidePlayer pl : players) {
                        new WaitMessage(pl.getOutputStream(),
                                playersNumber - numberOfConnectedPlayers).send();
                    }
                }

            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


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

    private void checkForPlayerDisconnect(ServerSidePlayer player) {
        byte b = -1;

        try {
            while (true) {
                Thread.sleep(200);
                if (gameStarted) {
                    break;
                } else if (player.getInputStream().available() > 0) {
                    b = (byte) player.getInputStream().read();
                    if (b == PlayerMessages.DISCONNECT.ordinal()) {
                        --numberOfConnectedPlayers;
                        players.remove(player);
                        for (ServerSidePlayer pl : players) {
                            new WaitMessage(pl.getOutputStream(), playersNumber - numberOfConnectedPlayers).send();
                        }
                        player.closeConnection();
                        break;
                    }
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //TODO
    //JEsli gracz sie rozlaczy
    // progress bar ile rund
    //czy jpg czy nie wysyla zdjecie lub nie
    //config gry plik propierties klasa properties
    //gra rozpocznie się za 30 sekund jak dołączą wszyscy gracze
    //Czy game jest dobrze zrobiony? Model szablonowy
    //MVC
    //Controller czy może zawierać obiekt View
    //foldery na Playera, czy na Server/Client + common
    //czas reakcji - local time serwera, klient wysyla roznice czasu na odpowiedz
    //Answer czy jest sens mieć interfejs, abstrakcyjną
    //klase pomocnicze typu RandomNumberWithRange itd
    //wspolbieznosc wtedy kiedy nie trzeba typu - czy musi czekac zawsze na odpowiedz od klientow, nawet kiedy nie beda mieli
    // mozliwosci odpowiadac - zastanowic nad deadlockami - wątek sterowany timerem
//testy czy mozna pisac klase anonimowe np serwera i tak testowac
    //do czegokolwiek działą, są rundy,najsłabszy gracz po 3 rundach zostaje wyrzucony
    //szerszy scenariusz gr, moze nastepowac eliminacja graczy


}