package pl.edu.agh.kis.ratespiel;

import pl.edu.agh.kis.Exception.EmptyQuestionFolderException;
import pl.edu.agh.kis.Exception.InvalidRangeException;
import pl.edu.agh.kis.Model.Answer;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.Model.Photo.QuestionServerSideWithPhoto;
import pl.edu.agh.kis.Model.Reply;
import pl.edu.agh.kis.Model.question.QuestionClientSide;
import pl.edu.agh.kis.Model.question.QuestionServerSide;
import pl.edu.agh.kis.Model.question.QuestionServerSideAbstract;
import pl.edu.agh.kis.messages.client.PlayerMessages;
import pl.edu.agh.kis.messages.server.*;
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
    private int waitingForPlayersAnswer;

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

    public boolean playRound() {
        boolean isImage = false;
        //Integer questionNumber = questionPath.split("/[0-9]+$")


        int questionNumber = getRandomNumberOfQuestion();
        File checkImage = new File(path + questionNumber + ".jpg");

        if (checkImage.exists()) {
            isImage = true;
        }
        QuestionServerSideAbstract q;
        if (isImage) {
            QuestionServerSideWithPhoto question = new QuestionServerSideWithPhoto(questionNumber, path);
            q = question;
            sendQuestionToPlayers(question, players);

        } else {
            QuestionServerSide question = new QuestionServerSide(questionNumber, path);
            q = question;
            sendQuestionToPlayers(question, players);

        }
        //sendQuestionToPlayers(new QuestionClientSideAbstract(question.getAnswers()), players,isImage);
        ArrayList<Answer> answers = new ArrayList<>();

        System.out.println("sendeeed");
        Vector<Thread> threads = new Vector<>();
        int i = 0;
        for (ServerSidePlayer player : players) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    getAnswer(answers, player, waitingForPlayersAnswer);
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
        if (players.size() == 1) {
            new WalkoverMessage(players.get(0).getOutputStream(), players.get(0).getPoints()).send();
            return false;
        }
        System.out.println("W");
        for (Answer answer :
                answers) {
            System.out.println(answer);
        }

        chooseWinnerOfRound(answers, q);
        return true;
    }

    protected boolean checkPlayersReady() {
        boolean check = true;
        for (ServerSidePlayer player : players
                ) {
            try {
                if (player.getInputStream().read() != 1) {
                    check = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return check;

    }

    protected void getAnswer(ArrayList<Answer> answers, ServerSidePlayer player, int maxTimeToReply) {
        BufferedReader br = new BufferedReader(new InputStreamReader(player.getInputStream()));
        try {
            Reminder r = new Reminder(maxTimeToReply);
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
            byte b = 0;
            while (true) {
                System.out.println("inside");
                b = (byte) player.inputStream.read();
                if (b == ServerMessages.QUESTION.ordinal()) {
                    System.out.println("przp");
                    ans = br.readLine();
                    timeString = br.readLine();
                    break;
                } else if (b == PlayerMessages.DISCONNECT.ordinal()) {
                    players.remove(player);
                    //new GoodbyeIfDisconnectedMessage(player.getOutputStream());
                    if (players.size() == 1) {
                        players.get(0).sendMessage(new WalkoverMessage(players.get(0).getOutputStream(),
                                players.get(0).getPoints()));

                    }
                    player.closeConnection();
                    break;
                }
            }
            if (timeString == null || ans == null) {
                answers.add(new Answer(null, player.getId()));
            } else {
                answers.add(new Answer(new Reply(ans, Long.parseLong(timeString)), player.getId()));
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR, REMOVING PLAYER " + player.getId());

            players.remove(player);
            //new GoodbyeIfDisconnectedMessage(player.getOutputStream());
            if (players.size() == 1) {
                players.get(0).sendMessage(new WalkoverMessage(players.get(0).getOutputStream(),
                        players.get(0).getPoints()));
            }
            player.closeConnection();
        }

    }

    //METODA RUN() Z SENDQUESTIONS + GETANSWERS
    //protected abstract void sendQuestionToPlayer(QuestionServerSideAbstract questionServerSide, PlayerServerSide player);

    //protected Answer getAnswer(PlayerServerSide player) {
    //return player.answer();
    //}

    protected int getRandomNumberOfQuestion() {
        int randomNumberOfFile = 0;
        try {
            randomNumberOfFile = new RandomNumberWithRange().randomInteger(1, numberOfQuestions());
        } catch (InvalidRangeException e) {
            e.printStackTrace();
        }

        if (randomNumberOfFile < 1) try {
            throw new EmptyQuestionFolderException("Not found any files in: " + path);
        } catch (EmptyQuestionFolderException emptyQuestionFolder) {
            emptyQuestionFolder.printStackTrace();
        }

        return randomNumberOfFile;
    }


    protected void sendQuestionToPlayers(QuestionServerSide question, ArrayList<ServerSidePlayer> players) {
        QuestionClientSide q = new QuestionClientSide(question);

        Vector<Thread> threads = new Vector<>();
        int i = 0;


        for (ServerSidePlayer player : players) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    new QuestionMessage(player.getOutputStream(), q).send();

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

    protected void sendQuestionToPlayers(QuestionServerSideWithPhoto question, ArrayList<ServerSidePlayer> players) {
        QuestionClientSideWithPhoto q = new QuestionClientSideWithPhoto(question);
        Vector<Thread> threads = new Vector<>();
        int i = 0;


        for (ServerSidePlayer player : players) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    new QuestionWithPhotoMessage(player.getOutputStream(), q).send();

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


/*        for (Answer answer : correctAnswers) {
            if (answer.getReply().getReplyTime() < quickestTime) {
                quickestTime = answer.getReply().getReplyTime();
            }
        }

        for (Answer answer : correctAnswers) {
            if (answer.getReply().getReplyTime() == quickestTime) {
                findPlayer(answer.getPlayerID()).addPoints(1); // for being quickest
            }
        }*/

        return -1;
    }

    public void chooseWinner() {
        int maxPoints = 0;
        int tmp = 0; //checking if draw

        for (ServerSidePlayer player : players) {
            if (maxPoints < player.getPoints()) {
                maxPoints = player.getPoints();
            }
        }

        for (ServerSidePlayer player : players) {
            if (maxPoints == player.getPoints()) {
                tmp++;
            }
        }

        tmp--;

        for (ServerSidePlayer player : players) {
            if (maxPoints == player.getPoints()) {
                byte b = 1;
                //sendInformationAboutResult(b, player);
                new EndOfGameMessage(player.getOutputStream(), true, tmp, player.getPoints()).send();

                System.out.println("player " + player.getId() + " won");
            } else {
                byte b = 0;
                new EndOfGameMessage(player.getOutputStream(), false, tmp, player.getPoints()).send();

                //sendInformationAboutResult(b, player);
            }
        }
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


    protected int numberOfQuestions() {
        try {
            File[] listOfFiles = new File(path).listFiles();
            int i = 0;
            for (File s : listOfFiles) {
                if (!s.getAbsolutePath().contains(".jpg")) {
                    i++;
                }
            }
            return i;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }
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