package pl.edu.agh.kis.player;

import pl.edu.agh.kis.Model.Reply;
import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.panels.FailPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Karl on 15.01.2017.
 */
public abstract class PlayerClientSideWithGUIAbstract extends PlayerClientSide {
    MainFrame mainFrame;
    volatile boolean isAnswering = false;
    private Container container;

    public PlayerClientSideWithGUIAbstract(Socket player, MainFrame mainFrame) {
        super(player);
        this.mainFrame = mainFrame;
    }

    private void clearMainFrame() {
        container = mainFrame.getContentPane();
        container.removeAll();

    }

    private JPanel pointsPanel(String points) {
        JPanel ret = new JPanel();
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("You had: " + points + " points");

        textPane.setEditable(false);
        ret.add(textPane);

        return ret;

    }

    protected void youWinInformation(String points) {
        clearMainFrame();
        container = mainFrame.getContentPane();
        String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\View\\";

        try {
            container.add(new JLabel(new ImageIcon(ImageIO.read(new File(path + "win.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainFrame.getContentPane().add(pointsPanel(points));
        mainFrame.pack();
        mainFrame.validate();
        mainFrame.repaint();
        mainFrame.setVisible(true);
    }

    protected void youFailedInformation(String points) {
        clearMainFrame();
        container.add(new FailPanel());

        mainFrame.getContentPane().add(pointsPanel(points));
        mainFrame.pack();

        mainFrame.validate();
        mainFrame.repaint();
        mainFrame.setVisible(true);
    }

    //CZY MĄDRZE Z ARGUMENTEM
    @Override
    public boolean sendReply(Reply reply) { //MAYBE SEND BACK THAT EVERYTHING OK FROM SERVER??
        printWriter.println(reply.getPlayerChoice());
        printWriter.println(reply.getReplyTime());
        System.out.println(reply.getReplyTime());
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
