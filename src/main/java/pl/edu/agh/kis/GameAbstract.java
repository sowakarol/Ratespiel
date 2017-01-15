package pl.edu.agh.kis;

import pl.edu.agh.kis.Exception.EmptyQuestionFolderException;
import pl.edu.agh.kis.Model.Answer;
import pl.edu.agh.kis.Model.PlayerServerSide;
import pl.edu.agh.kis.Model.QuestionServerSide;
import pl.edu.agh.kis.Model.QuestionServerSideAbstract;

import java.io.File;
import java.util.Vector;

/**
 * Created by Karl on 11.01.2017.
 */
public abstract class GameAbstract implements GameInterface {
    private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\Questions\\";
    protected Vector<PlayerServerSide> players = new Vector<PlayerServerSide>();
    protected int numberOfPlayers;
    /**
     * variable representing time in which player has to answer for question in seconds
     */
    int waitingForPlayersAnswer;

    public GameAbstract(int waitingForPlayersAnswer, PlayerServerSide... players) {
        this.numberOfPlayers = players.length;
        this.waitingForPlayersAnswer = waitingForPlayersAnswer;
        for (int i = 0; i < players.length; i++) {
            this.players.add(players[i]);
        }
    }

    public Vector<PlayerServerSide> getPlayers() {
        return players;
    }

    public void removeTheWorstPlayer() {
    }

    //METODA RUN() Z SENDQUESTIONS + GETANSWERS
    protected abstract void sendQuestionToPlayer(QuestionServerSideAbstract questionServerSide, PlayerServerSide player);

    protected Answer getAnswer(PlayerServerSide player) {
        return player.answer();
    }

    protected QuestionServerSide createQuestion() {
        int randomNumberOfFile = new RandomNumberWithRange().randomInteger(1, numberOfQuestions());


        if (randomNumberOfFile < 1) try {
            throw new EmptyQuestionFolderException("Not found any files in: " + path);
        } catch (EmptyQuestionFolderException emptyQuestionFolder) {
            emptyQuestionFolder.printStackTrace();
        }

        return new QuestionServerSide(randomNumberOfFile);
    }


    private synchronized Vector<Answer> getAnswers(Vector<PlayerServerSide> players) {
        Vector<Answer> answers = new Vector<>();
        for (PlayerServerSide player : players) {
            answers.add(player.answer());
        }
        return answers;
    }

    protected void sendQuestionToPlayers(QuestionServerSideAbstract questionServerSide, Vector<PlayerServerSide> players) {
        Vector<Thread> threads = new Vector<>();
        int i = 0;
        for (PlayerServerSide player : players) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    sendQuestionToPlayer(questionServerSide, player);

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

    protected int chooseWinnerOfRound(Vector<Answer> answers, QuestionServerSideAbstract question) {
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
            if (answer.getReply().getReplyTime() < quickestTime) {
                findPlayer(answer.getPlayerID()).addPoints(1); // for being quickest
            }
        }

        return -1;
    }


    private PlayerServerSide findPlayer(int id) {
        for (PlayerServerSide player : players
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
            return listOfFiles.length;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }

    protected boolean isOver() {
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

        for (Thread thread : threads
                ) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean ret = false;
        for (Boolean decision : quitDecisionsFromPlayers) {
            if (decision == true) ret = true;

        }
        System.out.println("IM OUT");
        return ret;
    }

}
