package pl.edu.agh.kis;

import pl.edu.agh.kis.Model.PlayerClientSide;
import pl.edu.agh.kis.Model.Reply;
import pl.edu.agh.kis.View.MainFrame;

import java.net.Socket;

/**
 * Created by Karl on 15.01.2017.
 */
public class PlayerClientSideWithGUIAbstract extends PlayerClientSide {
    MainFrame mainFrame;
    volatile boolean isAnswering = false;

    public PlayerClientSideWithGUIAbstract(Socket player, MainFrame mainFrame) {
        super(player);
        this.mainFrame = mainFrame;
    }


    //CZY MĄDRZE Z ARGUMENTEM
    @Override
    public boolean sendReply(Reply reply) { //MAYBE SEND BACK THAT EVERYTHING OK FROM SERVER??
        printWriter.println(reply.getPlayerChoice());
        printWriter.println(reply.getReplyTime());
        return true;
    }


    @Override
    public synchronized void play() {
        //for (int i = 0; i < 4; i++) {
        setAnswering(true);
        playRound();


        //}
    }

    public boolean isAnswering() {

        return isAnswering;
    }

    public void setAnswering(boolean answering) {
        isAnswering = answering;
    }


}
