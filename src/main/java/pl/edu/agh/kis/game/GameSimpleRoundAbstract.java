package pl.edu.agh.kis.game;

import pl.edu.agh.kis.Model.Answer;
import pl.edu.agh.kis.Model.question.QuestionServerSideAbstract;
import pl.edu.agh.kis.player.PlayerServerSide;

import java.util.Vector;

/**
 * Created by Karl on 15.01.2017.
 */
public abstract class GameSimpleRoundAbstract extends GameAbstract {
    public GameSimpleRoundAbstract(int waitingForPlayersAnswer, PlayerServerSide... players) {
        super(waitingForPlayersAnswer, players);
    }

    public void playRound() {
        QuestionServerSideAbstract question = createQuestion();

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
        System.out.println("W");
        for (Answer answer :
                answers) {
            System.out.println(answer);
        }

        chooseWinnerOfRound(answers, question);
    }


    public void play() { //do czegokolwiek działą, są rundy,najsłabszy gracz po 3 rundach zostaje wyrzucony
        //szerszy scenariusz gr, moze nastepowac eliminacja graczy
        for (int i = 0; i < 4; i++) {
            playRound();
        }
        chooseWinner();

    }
}
