package pl.edu.agh.kis.ratespiel;

import pl.edu.agh.kis.server.ServerSidePlayer;

import java.util.ArrayList;

/**
 * Created by Karl on 23.01.2017.
 */
public class GameBasicVersion extends GameAbstract {
    public GameBasicVersion(ArrayList<ServerSidePlayer> players, int waitingForPlayersAnswer, String path, int roundNumbers) {
        super(players, waitingForPlayersAnswer, path, roundNumbers);
    }

    @Override
    public void play() {
        checkPlayersReady();
        for (int i = 0; i < roundNumbers; i++) {
            if (!playRound()) break;

        }
        chooseWinner();
    }
}
