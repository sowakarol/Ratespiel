package pl.edu.agh.kis;

/**
 * Created by Karl on 08.01.2017.
 */
public class GameServerSideForTwoPlayers extends GameServerSide {
    GameServerSideForTwoPlayers(PlayerServerSide player1, PlayerServerSide player2) {
        getPlayers().add(player1);
        getPlayers().add(player2);
    }
}
