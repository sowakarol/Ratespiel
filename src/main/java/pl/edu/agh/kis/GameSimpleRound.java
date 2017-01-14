package pl.edu.agh.kis;

import pl.edu.agh.kis.Model.Answer;
import pl.edu.agh.kis.Model.PlayerServerSide;
import pl.edu.agh.kis.Model.QuestionServerSide;

import java.util.Vector;

/**
 * Created by Karl on 12.01.2017.
 */
public class GameSimpleRound extends GameAbstract {
    public GameSimpleRound(int waitingForPlayersAnswer, PlayerServerSide... players) {
        super(waitingForPlayersAnswer, players);
    }

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

}
