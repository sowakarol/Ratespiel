package pl.edu.agh.kis.ratespiel;

import pl.edu.agh.kis.server.ServerSidePlayer;

import java.util.ArrayList;

/**
 * Created by Karl on 23.01.2017.
 * Basic version of a game
 */
public class GameBasicVersion extends GameAbstract {
    /**
     * @param players                 all connected players who are going to play
     * @param waitingForPlayersAnswer
     * @param path
     * @param roundNumbers
     */
    public GameBasicVersion(ArrayList<ServerSidePlayer> players, int waitingForPlayersAnswer, String path, int roundNumbers) {
        super(players, waitingForPlayersAnswer, path, roundNumbers);
    }

    /**
     * simple play() method
     */
    @Override
    public void play() {
        checkPlayersReady();
        for (int i = 0; i < roundNumbers; i++) {
            if (!playRound()) break;

        }
        chooseWinner();
    }
}
