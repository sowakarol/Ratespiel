package pl.edu.agh.kis.Model;

import java.io.*;
import java.net.Socket;

/**
 * Created by Karl on 07.01.2017.
 */
public abstract class PlayerAbstract {
    protected Socket player;
    protected PrintWriter printWriter;
    protected BufferedReader bufferedReader;
    protected OutputStream outputStream;
    protected InputStream inputStream;
    protected int points = 0;


    PlayerAbstract(Socket player) {
        this.player = player;
        try {
            outputStream = player.getOutputStream();
            inputStream = player.getInputStream();
            printWriter = new PrintWriter(outputStream, true);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPoints(int numberOfPoints) {
        points += numberOfPoints;
    }

    public void closeConnection() {
        printWriter.close();
        try {
            bufferedReader.close();
            player.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
