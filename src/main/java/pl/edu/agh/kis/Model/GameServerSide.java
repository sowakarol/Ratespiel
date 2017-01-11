package pl.edu.agh.kis.Model;

import pl.edu.agh.kis.Exception.EmptyQuestionFolderException;

import java.io.File;
import java.util.Vector;

/**
 * Created by Karl on 07.01.2017.
 */
public abstract class GameServerSide implements GameServerSideInterface {
    //Czy game jest dobrze zrobiony? Model szablonowy
    //MVC
    //Controller czy może zawierać obiekt View
    //foldery na Playera, czy na Server/Client + common
    //czas reakcji - local time serwera, klient wysyla roznice czasu na odpowiedz
    //Answer czy jest sens mieć interfejs, abstrakcyjną
    //klase pomocnicze typu RandomNumberWithRange itd
    //wspolbieznosc wtedy kiedy nie trzeba typu - czy musi czekac zawsze na odpowiedz od klientow, nawet kiedy nie beda mieli
    // mozliwosci odpowiadac - zastanowic nad deadlockami - wątek sterowany timerem
    //testy czy mozna pisac klase anonimowe np serwera i tak testowac


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


    public void play() { //do czegokolwiek działą, są rundy,najsłabszy gracz po 3 rundach zostaje wyrzucony
        playRound(); // szerszy scenariusz gr, moze nastepowac eliminacja graczy
        while (!isOver()) {
            playRound();
        }
    }


    public void playRound() {
        QuestionServerSide question = createQuestion();

        sendQuestionToPlayers(question, players);
        Vector<Answer> answers = new Vector<>();
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

    private boolean isOver() {
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
