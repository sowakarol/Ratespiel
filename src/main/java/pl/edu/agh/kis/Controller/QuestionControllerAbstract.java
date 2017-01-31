package pl.edu.agh.kis.Controller;

import pl.edu.agh.kis.View.MainFrame;
import pl.edu.agh.kis.client.ClientSidePlayer;
import pl.edu.agh.kis.messages.client.PlayerAnsweredMessage;
import pl.edu.agh.kis.panels.AnswerSentPanel;
import pl.edu.agh.kis.panels.HowManyRoundsLeftPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Karl on 15.01.2017.
 * Class taking care of getting input from Client View classes and taking actions depended on this input.
 */
public class QuestionControllerAbstract implements ActionListener {
    /**
     * true if an answer was chosen, false otherwise
     */
    private boolean clicked = false;
    /**
     * an Answering player
     */
    private ClientSidePlayer player;
    /**
     * time when the question was delivered to a player
     */
    private long deliveredTime;
    /**
     * time in which player answered for a question
     */
    private long answerTime;
    /**
     * a String representing a player's chosen answer
     */
    private String chosenAnswer;
    /**
     * Answering player's main frame for GUI changes
     */
    private MainFrame mainFrame;
    /**
     * Variable used in clearing player's GUI
     */
    private Container container;
    /**
     * variable representing number of Rounds left
     */
    private int howManyLoopsLeft;

    /**
     * @param deliveredTime    to initialize deliveredTime variable
     * @param player           to initialize player variable
     * @param mainFrame        to initialize mainFrame variable
     * @param howManyLoopsLeft to initialize howManyLoopsLeft variable
     */
    public QuestionControllerAbstract(long deliveredTime, ClientSidePlayer player, MainFrame mainFrame, int howManyLoopsLeft) {
        this.player = player;
        this.deliveredTime = deliveredTime;
        this.mainFrame = mainFrame;
        this.howManyLoopsLeft = howManyLoopsLeft;
    }


    /**
     * getter of deliveredTime variable
     * @return deliveredTime
     */
    public long getDeliveredTime() {
        return deliveredTime;
    }

    /**
     * setter of deliveredTime variable
     * @param deliveredTime a new value of deliveredTime
     */
    public void setDeliveredTime(long deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    /**
     * getter of answerTime variable
     * @return anwserTime variable
     */
    public long getAnswerTime() {
        return answerTime;
    }

    /**
     * setter of answerTime variable
     * @param answerTime new value of answerTime variable
     */
    public void setAnswerTime(long answerTime) {
        this.answerTime = answerTime;
    }

    /**
     * getter of chosenAnswer variable
     * @return chosenAnswer variable
     */
    public String getChosenAnswer() {
        return chosenAnswer;
    }


    /**
     * @return value of clicked variable
     */
    public boolean isClicked() {
        return clicked;
    }

    /**
     * setter of clicked variable
     * @param clicked new clicked variable value
     */
    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }


    /**
     * method saving chosenAnswer after a button with answer is clicked
     * @param e ActionEvent - couple buttons are connected to this action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //if a buttons with answers weren't clicked initialize chosenAnswer with answerTime
        if (!clicked) {
            chosenAnswer = e.getActionCommand();
            answerTime = System.nanoTime();
            clicked = true;
            //clear mainFrame
            container = mainFrame.getContentPane();
            container.removeAll();
            new PlayerAnsweredMessage(player.getOutputStream()).send();
            Dimension d = mainFrame.getSize();

            //add AnswerSentPanel and HowManyRoundsLeftPanel to mainframe
            mainFrame.getContentPane().add(new AnswerSentPanel());
            mainFrame.pack();
            mainFrame.getContentPane().add(new HowManyRoundsLeftPanel(howManyLoopsLeft));
            mainFrame.pack();
            mainFrame.validate();
            mainFrame.repaint();
            mainFrame.setSize(d);
            mainFrame.setVisible(true);
        }
    }
}
