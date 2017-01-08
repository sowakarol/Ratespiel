package pl.edu.agh.kis;

import pl.edu.agh.kis.Exception.EmptyQuestionFolderException;

import java.io.File;
import java.util.Vector;

/**
 * Created by Karl on 07.01.2017.
 */
public abstract class GameServerSide implements GameServerSideInterface {
    private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\";
    private Vector<PlayerServerSide> players = new Vector<PlayerServerSide>();

   /* GameServerSide(PlayerServerSide player1, PlayerServerSide player2) {
        this.player1 = player1;
        this.player2 = player2;
    }*/

    public Vector<PlayerServerSide> getPlayers() {
        return players;
    }
    /* TODO
* chooseWinner
* multithreading
* Vector of players
* play()
*/

    /*GameServerSide(PlayerServerSide player1) {
        this.player1 = player1;
    }*/


    public void play() {
        playRound();
        /*while(!isOver()){
            playRound();
        }*/
    }


    public void playRound() { // WITHOUT MULTITHREAD
        QuestionServerSide question = createQuestion();

        sendQuestionToPlayers(question, players);
        Vector<Answer> answers = new Vector<>(); //initialCapacity zależne od playersNumber
        Vector<Thread> threads = new Vector<>();
        int i = 0;
        for (PlayerServerSide player : players) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    answers.add(getAnswer(player));
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

        for (Answer answer :
                answers) {
            System.out.println(answer);
        }

        System.out.println("NIGGA WE MADE IT");

        chooseWinner(answers, question);

    }

    private synchronized Vector<Answer> getAnswers(Vector<PlayerServerSide> players) {
        Vector<Answer> answers = new Vector<>();
        for (PlayerServerSide player : players) {
            answers.add(player.answer());
        }
        return answers;
    }

    private void sendQuestionToPlayers(QuestionServerSide questionServerSide, Vector<PlayerServerSide> players) {
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

    private int chooseWinner(Vector<Answer> answers, QuestionServerSide question) {
        Vector<Answer> correctAnswers = new Vector<>();
        for (Answer answer : answers) {
            if (answer.isTrue(question)) {
                correctAnswers.add(answer);
                System.out.println("PLAYER " + answer.getPlayerID() + " CORRECT ANSWER");
            }
        }

        return -1;
//check DRAW
    }


    private int findQuickestAnswer(Vector<Answer> answers) {
        return -1;
    }

    //METODA RUN() Z SENDQUESTIONS + GETANSWERS
    private void sendQuestionToPlayer(QuestionServerSide questionServerSide, PlayerServerSide player) {
        QuestionClientSide questionToSend = new QuestionClientSide(questionServerSide.getAnswers(), questionServerSide.toTranslate);
        player.sendQuestion(questionToSend);
    }

    private Answer getAnswer(PlayerServerSide player) {
        return player.answer();
    }

    private QuestionServerSide createQuestion() {
        int randomNumberOfFile = new RandomNumberWithRange().randomInteger(1, numberOfQuestions());


        if (randomNumberOfFile < 1) try {
            throw new EmptyQuestionFolderException("Not found any files in: " + path);
        } catch (EmptyQuestionFolderException emptyQuestionFolder) {
            emptyQuestionFolder.printStackTrace();
        }

        return new QuestionServerSide(randomNumberOfFile);


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
}
