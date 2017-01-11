package pl.edu.agh.kis.Model;

/**
 * Created by Karl on 08.01.2017.
 */
public class GameServerSideForOnePlayer extends GameServerSide {
    GameServerSideForOnePlayer(PlayerServerSide player1) {
        getPlayers().add(player1);
    }
}
