package pl.edu.agh.kis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by Karl on 07.01.2017.
 */
public class Server implements Runnable {
    ServerSocket serverSocket;
    Vector<PlayerServerSide> players = new Vector<>(2);
    int playersNumber;

    public Server(int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            Socket playerSocket = serverSocket.accept();
            PlayerServerSide player = new PlayerServerSide(playerSocket, ++playersNumber);
            addToPlayersList(player);
            if (players.size() == 2) {


            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
