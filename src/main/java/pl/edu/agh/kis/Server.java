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
    //Vector<PlayerServerSide> wektor graczy

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

    private synchronized boolean addToPlayersList() {
        return false;
    }

    public void listenAndPreparePlayer() {
        try {
            Socket playerSocket = serverSocket.accept();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
