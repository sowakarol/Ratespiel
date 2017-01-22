package pl.edu.agh.kis.ratespiel;

import pl.edu.agh.kis.messages.MessageAbstract;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Karl on 22.01.2017.
 * abstract class refactoring code to player on both server/client side
 */
public abstract class PlayerAbstract {
    protected Socket playerSocket;
    protected OutputStream outputStream;
    protected InputStream inputStream;


    public PlayerAbstract(Socket playerSocket) {
        this.playerSocket = playerSocket;
        try {
            outputStream = playerSocket.getOutputStream();
            inputStream = playerSocket.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(MessageAbstract message) {
        message.send();
    }

    public Socket getPlayerSocket() {
        return playerSocket;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void closeConnection() {
        try {
            playerSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
