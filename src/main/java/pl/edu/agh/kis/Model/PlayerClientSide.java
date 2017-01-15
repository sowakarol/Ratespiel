package pl.edu.agh.kis.Model;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Karl on 07.01.2017.
 */
public class PlayerClientSide extends PlayerAbstract implements PlayerClientSideInterface {
    public PlayerClientSide(Socket player) {
        super(player);
    }


    @Override
    public QuestionClientSide getQuestion() {
        try {
            String toTranslate = bufferedReader.readLine();
            Vector<String> answers = new Vector<>(4);
            for (int i = 0; i < 4; i++) {
                answers.add(bufferedReader.readLine());
            }
            return new QuestionClientSide(answers, toTranslate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //CZY MĄDRZE Z ARGUMENTEM
    @Override
    public boolean sendReply(Reply reply) { //MAYBE SEND BACK THAT EVERYTHING OK FROM SERVER??
        printWriter.println(reply.getPlayerChoice());
        printWriter.println(reply.getReplyTime());
        return true;
    }


    //ASK IF quit CAN RETURN TRUE!!!!
    @Override
    public boolean quit(boolean playerChoice) {
        if (playerChoice) {
            try {
                outputStream.write(1);
                closeConnection();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                outputStream.write(0);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void play() {
        playRound();
        while (!sendDecisionIsOver()) {
            playRound();
        }

    }


    protected void youWinInformation(String points) {
        System.out.println("You win with " + points + " points");
    }

    protected void youFailedInformation(String points) {
        System.out.println("You failed with " + points + " points");
    }

    @Override
    public boolean getResult() {
        try {
            String b = bufferedReader.readLine();
            String points = bufferedReader.readLine();
            if (b.equals("1")) {
                youWinInformation(points);
            } else {
                youFailedInformation(points);
            }
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    private Reply reply() {
        Scanner sc = new Scanner(System.in);
        System.out.println("REPLY::");
        String playerChoice = "";

        playerChoice = sc.nextLine();
        System.out.println("Saved");

        return new Reply(playerChoice, System.nanoTime());
    }

    private boolean sendDecisionIsOver() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Quit??");
        String playerChoice = "";

        playerChoice = sc.nextLine();
        if (playerChoice.equals("Questions/1")) {
            quit(true);
            closeConnection();
            return true;
        } else {
            quit(false);
            return false;
        }
    }

    public void playRound() {
        QuestionClientSide question = getQuestion();
        System.out.println(question);

        sendReply(reply());
        System.out.println("Sended");
        sendDecisionIsOver();
    }

}
