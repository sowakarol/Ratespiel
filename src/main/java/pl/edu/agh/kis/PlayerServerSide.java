package pl.edu.agh.kis;

import java.net.Socket;

/**
 * Created by Karl on 07.01.2017.
 */
public class PlayerServerSide implements PlayerServerSideInterface {
    Socket player;
    int id;


    PlayerServerSide(Socket player, int id) {
        this.player = player;
        this.id = id;
    }


    @Override
    public boolean sendQuestion(QuestionServerSide question) {
        return false;
    }

    @Override
    public Answer answer(Reply reply) {
        return null;
    }

    @Override
    public Reply getReply() {
        return null;
    }
}
