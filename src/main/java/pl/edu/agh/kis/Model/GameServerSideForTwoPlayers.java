package pl.edu.agh.kis.Model;

/**
 * Created by Karl on 08.01.2017.
 */
public class GameServerSideForTwoPlayers extends GameServerSide { // ilosc graczy jako parametr, gra powinna sie roznic zasadami
    GameServerSideForTwoPlayers(PlayerServerSide player1, PlayerServerSide player2) {
        getPlayers().add(player1);
        getPlayers().add(player2);
    }
}
