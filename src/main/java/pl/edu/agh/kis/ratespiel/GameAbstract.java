package pl.edu.agh.kis.ratespiel;

import pl.edu.agh.kis.Exception.EmptyQuestionFolderException;
import pl.edu.agh.kis.Exception.InvalidRangeException;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.Model.Photo.QuestionServerSideWithPhoto;
import pl.edu.agh.kis.Model.question.QuestionClientSide;
import pl.edu.agh.kis.Model.question.QuestionServerSide;
import pl.edu.agh.kis.Model.question.QuestionServerSideAbstract;
import pl.edu.agh.kis.answer.Answer;
import pl.edu.agh.kis.answer.Reply;
import pl.edu.agh.kis.messages.client.PlayerMessages;
import pl.edu.agh.kis.messages.server.*;
import pl.edu.agh.kis.server.ServerSidePlayer;
import pl.edu.agh.kis.utils.AnswerChecker;
import pl.edu.agh.kis.utils.RandomNumberWithRange;

import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Created by Karl on 23.01.2017.
 * Class representing core of standard game
 */
public abstract class GameAbstract implements GameInterface {
    /**
     * path to directory with questions
     */
    protected String path;
    /**
     * players in this game
     */
    protected ArrayList<ServerSidePlayer> players = new ArrayList<ServerSidePlayer>();
    /**
     * number of players
     */
    protected int numberOfPlayers;
    /**
     * number of rounds
     */
    protected int roundNumbers;
    /**
     * variable counting threads
     */
    private int threadCount;

    /**
     * variable representing time in which player has to answer for question in seconds
     */
    private int waitingForPlayersAnswer;

    /**
     * @param players                 players playing the game
     * @param waitingForPlayersAnswer number of seconds for waiting for players answer
     * @param path                    path to directory of questions
     * @param roundNumbers            number of rounds
     */
    public GameAbstract(ArrayList<ServerSidePlayer> players, int waitingForPlayersAnswer, String path, int roundNumbers) {
        this.numberOfPlayers = players.size();
        this.waitingForPlayersAnswer = waitingForPlayersAnswer;
        for (ServerSidePlayer player : players) {
            this.players.add(player);
        }
        this.path = path;
        this.roundNumbers = roundNumbers;
    }


    /**
     * in future functionalities might be useful to change gameplay
     */
    public void removeTheWorstPlayer() {
    }

    /**
     * @return true if round was played without any disconnections
     */
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

        final ArrayList<Answer> answers = new ArrayList<Answer>();

        Vector<Thread> threads = new Vector<Thread>();
        int i = 0;
        for (final ServerSidePlayer player : players) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    getAnswer(answers, player, waitingForPlayersAnswer);
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
        if (players.size() == 1) {
            new WalkoverMessage(players.get(0).getOutputStream(), players.get(0).getPoints()).send();
            return false;
        }
        for (Answer answer :
                answers) {
            System.out.println(answer);
        }

        chooseWinnerOfRound(answers, q);
        return true;
    }

    /**
     * @return true if all players are ready for a game
     */
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

    /**
     * @param answers arraylist with all players' answers
     * @param player player which will answer
     * @param maxTimeToReply time in which player must reply
     */
    protected void getAnswer(ArrayList<Answer> answers, ServerSidePlayer player, int maxTimeToReply) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(player.getInputStream(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            Reminder r = new Reminder(maxTimeToReply);
            String ans = null;
            boolean thisOne = false;
            String timeString = null;
            /*while (!r.getTimePassed()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            byte b1 = -1;
            while (answers.size() < 1) {
                if ((byte) player.getInputStream().available() > 0) {
                    b1 = (byte) player.getInputStream().read();
                }
                if (b1 == PlayerMessages.PLAYER_ANSWERED_MESSAGE.ordinal()) {
                    System.out.println("GOT IT");
                    thisOne = true;
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            new GetAnswerMessage(player.getOutputStream()).send();
            byte b = 0;
            while (true) {
                b = (byte) player.inputStream.read();
                if (b == ServerMessages.QUESTION.ordinal()) {
                    ans = br.readLine();
                    timeString = br.readLine();
                    break;
                } else if (b == PlayerMessages.DISCONNECT.ordinal()) {
                    players.remove(player);
                    if (players.size() == 1) {
                        players.get(0).sendMessage(new WalkoverMessage(players.get(0).getOutputStream(),
                                players.get(0).getPoints()));

                    }
                    player.closeConnection();
                    break;
                }
            }
            if (timeString == null || ans == null || !thisOne) {
                //answers.add(new Answer(null, player.getId()));
            } else {
                answers.add(new Answer(new Reply(ans, Long.parseLong(timeString)), player.getId()));
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR, REMOVING PLAYER " + player.getId());

            players.remove(player);
            if (players.size() == 1) {
                players.get(0).sendMessage(new WalkoverMessage(players.get(0).getOutputStream(),
                        players.get(0).getPoints()));
            }
            player.closeConnection();
        }
    }


    /**
     * @return random number of question
     */
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


    /**
     * @param question question from server side
     * @param players all players playing
     */
    protected void sendQuestionToPlayers(QuestionServerSide question, ArrayList<ServerSidePlayer> players) {
        final QuestionClientSide q;
        synchronized (this) {
            q = new QuestionClientSide(question);
        }
        for (int i = 0; i < q.getAnswers().size(); i++) {
            System.out.println("X" + q.getAnswers().get(i));
        }

        Vector<Thread> threads = new Vector<Thread>();
        threadCount = -1;


        for (final ServerSidePlayer player : players) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    new QuestionMessage(player.getOutputStream(), new QuestionClientSide(q)).send();

                }
            }));
            threadCount++;
            threads.get(threadCount).start();
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

    /**
     * @param question question to be sent
     * @param players all players who are playing
     */
    protected void sendQuestionToPlayers(QuestionServerSideWithPhoto question, ArrayList<ServerSidePlayer> players) {
        final QuestionClientSideWithPhoto q = new QuestionClientSideWithPhoto(question);
        Vector<Thread> threads = new Vector<Thread>();
        int i = 0;

        for (final ServerSidePlayer player : players) {
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

    /**
     * @param answers all answers from all players in a round
     * @param question question which was asked in a round
     * @return id of a player
     */
    protected int chooseWinnerOfRound(ArrayList<Answer> answers, QuestionServerSideAbstract question) {
        Vector<Answer> correctAnswers = new Vector<Answer>();
        AnswerChecker checker = new AnswerChecker();
        for (Answer answer : answers) {
            if (checker.isTrue(question, answer)) {
                correctAnswers.add(answer);
                System.out.println("PLAYER " + answer.getPlayerID() + " CORRECT ANSWER");
                findPlayer(answer.getPlayerID()).addPoints(1); // for correct answer
            } else {
                findPlayer(answer.getPlayerID()).addPoints(-1); // for notcorrect answer
                System.out.println(findPlayer(answer.getPlayerID()).getPoints());
            }
        }
        long quickestTime = Long.MAX_VALUE;

        /*for (Answer answer : correctAnswers) {
            if (answer.getReply().getReplyTime() < quickestTime) {
                System.out.println(quickestTime);
                quickestTime = answer.getReply().getReplyTime();
            }
        }*/

        /*for (Answer answer : correctAnswers) {
            if (answer.getReply().getReplyTime() == quickestTime) {
                System.out.println("QUICKEST" + answer.getPlayerID());
                findPlayer(answer.getPlayerID()).addPoints(1); // for being quickest
            }
        }*/

        return -1;
    }

    /**
     * choosing winner of a game and sending messages about result to all players
     */
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


    /**
     * @param id id of desired player
     * @return reference to player from server side
     */
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

}