package pl.edu.agh.kis.ratespiel;

import pl.edu.agh.kis.Exception.EmptyQuestionFolderException;
import pl.edu.agh.kis.Model.Answer;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.Model.Photo.QuestionServerSideWithPhoto;
import pl.edu.agh.kis.Model.Reply;
import pl.edu.agh.kis.Model.question.QuestionClientSide;
import pl.edu.agh.kis.Model.question.QuestionServerSide;
import pl.edu.agh.kis.Model.question.QuestionServerSideAbstract;
import pl.edu.agh.kis.messages.server.GetAnswerMessage;
import pl.edu.agh.kis.messages.server.QuestionMessage;
import pl.edu.agh.kis.messages.server.QuestionWithPhotoMessage;
import pl.edu.agh.kis.server.PlayerServerSide;
import pl.edu.agh.kis.server.ServerSidePlayer;
import pl.edu.agh.kis.utils.AnswerChecker;
import pl.edu.agh.kis.utils.RandomNumberWithRange;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Created by Karl on 23.01.2017.
 */
public abstract class GameAbstract implements GameInterface {
    protected String path;
    protected ArrayList<ServerSidePlayer> players = new ArrayList<>();
    protected int numberOfPlayers;
    protected int roundNumbers;
    /**
     * variable representing time in which player has to answer for question in seconds
     */
    int waitingForPlayersAnswer;

    public GameAbstract(ArrayList<ServerSidePlayer> players, int waitingForPlayersAnswer, String path, int roundNumbers) {
        this.numberOfPlayers = players.size();
        this.waitingForPlayersAnswer = waitingForPlayersAnswer;
        for (ServerSidePlayer player : players) {
            this.players.add(player);
        }
        this.path = path;
        this.roundNumbers = roundNumbers;
    }


    public void removeTheWorstPlayer() {
    }

    public void playRound() {
        boolean isImage = false;
        //Integer questionNumber = questionPath.split("/[0-9]+$")


        int questionNumber = getRandomNumberOfQuestion();
        File checkImage = new File(path + questionNumber + ".jpg");

        if (checkImage.exists()) {
            isImage = true;
        }
        QuestionServerSideAbstract q;
        if (isImage) {
            QuestionServerSideWithPhoto question = new QuestionServerSideWithPhoto(questionNumber);
            q = question;
            sendQuestionToPlayers(new QuestionClientSideWithPhoto(question), players);

        } else {
            QuestionServerSide question = new QuestionServerSide(questionNumber);
            q = question;
            sendQuestionToPlayers(new QuestionClientSide(question), players);

        }
        //sendQuestionToPlayers(new QuestionClientSideAbstract(question.getAnswers()), players,isImage);
        ArrayList<Answer> answers = new ArrayList<>();


        Vector<Thread> threads = new Vector<>();
        int i = 0;
        for (ServerSidePlayer player : players) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    getAnswer(answers, player, waitingForPlayersAnswer * 2);
                }
            }));
            threads.get(i).start();
            i++;
        }
        for (Thread thread : threads
                ) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("W");
        for (Answer answer :
                answers) {
            System.out.println(answer);
        }

        chooseWinnerOfRound(answers, q);
    }


    protected void getAnswer(ArrayList<Answer> answers, ServerSidePlayer player, int maxTimeToReply) {
        BufferedReader br = new BufferedReader(new InputStreamReader(player.getInputStream()));
        try {
            Reminder r = new Reminder(maxTimeToReply * 2);
            String ans = null;
            String timeString = null;
            while (!r.getTimePassed()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            new GetAnswerMessage(player.getOutputStream()).send();
            ans = br.readLine();
            timeString = br.readLine();

            if (timeString == null || ans == null) {
                answers.add(new Answer(null, player.getId()));
            } else {
                answers.add(new Answer(new Reply(ans, Long.parseLong(timeString)), player.getId()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //METODA RUN() Z SENDQUESTIONS + GETANSWERS
    //protected abstract void sendQuestionToPlayer(QuestionServerSideAbstract questionServerSide, PlayerServerSide player);

    //protected Answer getAnswer(PlayerServerSide player) {
    //return player.answer();
    //}

    protected int getRandomNumberOfQuestion() {
        int randomNumberOfFile = new RandomNumberWithRange().randomInteger(1, numberOfQuestions());

        if (randomNumberOfFile < 1) try {
            throw new EmptyQuestionFolderException("Not found any files in: " + path);
        } catch (EmptyQuestionFolderException emptyQuestionFolder) {
            emptyQuestionFolder.printStackTrace();
        }

        return randomNumberOfFile;
    }


    private synchronized Vector<Answer> getAnswers(Vector<PlayerServerSide> players) {
        Vector<Answer> answers = new Vector<>();
        for (PlayerServerSide player : players) {
            answers.add(player.answer());
        }
        return answers;
    }

    protected void sendQuestionToPlayers(QuestionClientSide question, ArrayList<ServerSidePlayer> players) {

        Vector<Thread> threads = new Vector<>();
        int i = 0;


        for (ServerSidePlayer player : players) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    new QuestionMessage(player.getOutputStream(), question).send();

                }
            }));
            threads.get(i).start();
            i++;
        }


        for (Thread thread : threads
                ) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void sendQuestionToPlayers(QuestionClientSideWithPhoto question, ArrayList<ServerSidePlayer> players) {

        Vector<Thread> threads = new Vector<>();
        int i = 0;


        for (ServerSidePlayer player : players) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    new QuestionWithPhotoMessage(player.getOutputStream(), question).send();

                }
            }));
            threads.get(i).start();
            i++;
        }


        for (Thread thread : threads
                ) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected int chooseWinnerOfRound(ArrayList<Answer> answers, QuestionServerSideAbstract question) {
        Vector<Answer> correctAnswers = new Vector<>();
        AnswerChecker checker = new AnswerChecker();
        for (Answer answer : answers) {
            if (checker.isTrue(question, answer)) {
                correctAnswers.add(answer);
                System.out.println("PLAYER " + answer.getPlayerID() + " CORRECT ANSWER");
                findPlayer(answer.getPlayerID()).addPoints(1); // for correct answer
            }
        }

        long quickestTime = Long.MAX_VALUE;


        for (Answer answer : correctAnswers) {
            if (answer.getReply().getReplyTime() < quickestTime) {
                quickestTime = answer.getReply().getReplyTime();
            }
        }

        for (Answer answer : correctAnswers) {
            if (answer.getReply().getReplyTime() == quickestTime) {
                findPlayer(answer.getPlayerID()).addPoints(1); // for being quickest
            }
        }

        return -1;
    }

    public void chooseWinner() {
        int maxPoints = 0;
        for (ServerSidePlayer player : players) {
            if (maxPoints < player.getPoints()) {
                maxPoints = player.getPoints();
            }
        }

        for (ServerSidePlayer player : players) {
            if (maxPoints == player.getPoints()) {
                byte b = 1;
                //sendInformationAboutResult(b, player);
                System.out.println("player " + player.getId() + " won");
            } else {
                byte b = 0;
                //sendInformationAboutResult(b, player);
            }
        }
    }


    //TODO
    //b==-1 DRAW with other player

    /**
     * @param b      if b==1 player win, b==0 player lose
     * @param player player who is informed by server about result
     */
    private void sendInformationAboutResult(byte b, PlayerServerSide player) {
        player.sendResult(b);
    }

    private ServerSidePlayer findPlayer(int id) {
        for (ServerSidePlayer player : players
                ) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }

    private Boolean getQuitDecision(PlayerServerSide player) {
        return player.quit();
    }

    protected int numberOfQuestions() {
        try {
            File[] listOfFiles = new File(path).listFiles();
            int i = 0;
            for (File s : listOfFiles) {
                if (!s.getAbsolutePath().contains(".jpg")) {
                    i++;
                }
            }
            if (listOfFiles != null) {
                return i;
            } else {
                return 0;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*protected boolean isOver() {
        System.out.println("IM HERE");
        Vector<Boolean> quitDecisionsFromPlayers = new Vector<>();
        Vector<Thread> threads = new Vector<>();
        int i = 0;
        for (PlayerServerSide player : players) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    quitDecisionsFromPlayers.add(getQuitDecision(player));
                }
            }));
            threads.get(i).start();
            i++;
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean ret = false;
        for (Boolean decision : quitDecisionsFromPlayers) {
            if (decision) ret = true;

        }
        System.out.println("IM OUT");
        return ret;
    }*/
}

class Reminder {
    private Timer timer;
    private boolean timePassed;

    public Reminder(int seconds) {
        timer = new Timer();
        timePassed = false;
        timer.schedule(new RemindTask(), seconds * 1000);
    }

    public boolean getTimePassed() {
        return timePassed;
    }

    class RemindTask extends TimerTask {
        public void run() {
            timePassed = true;
            timer.cancel(); //Wyłączamy taska
        }
    }

    /*public static void main(String args[]) {
        new Reminder(5);
        System.out.format("Task scheduled.%n");
    }*/
}