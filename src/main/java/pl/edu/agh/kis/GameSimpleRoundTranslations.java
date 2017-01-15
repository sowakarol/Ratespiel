package pl.edu.agh.kis;

import pl.edu.agh.kis.Exception.EmptyQuestionFolderException;
import pl.edu.agh.kis.Model.Photo.QuestionServerSideWithPhoto;
import pl.edu.agh.kis.Model.PlayerServerSide;
import pl.edu.agh.kis.Model.QuestionClientSide;
import pl.edu.agh.kis.Model.QuestionServerSide;
import pl.edu.agh.kis.Model.QuestionServerSideAbstract;

/**
 * Created by Karl on 15.01.2017.
 */
public class GameSimpleRoundTranslations extends GameSimpleRoundAbstract {
    private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\Questions\\";

    public GameSimpleRoundTranslations(int waitingForPlayersAnswer, PlayerServerSide... players) {
        super(waitingForPlayersAnswer, players);
    }

    @Override
    protected void sendQuestionToPlayer(QuestionServerSideAbstract questionServerSide, PlayerServerSide player) {
        QuestionServerSide question = (QuestionServerSide) questionServerSide;
        QuestionClientSide questionToSend = new QuestionClientSide(question);
        questionToSend.randomizeAnswers();
        player.sendQuestion(questionToSend);
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
        //szerszy scenariusz gr, moze nastepowac eliminacja graczy
        for (int i = 0; i < 20; i++) {
            playRound();
        }

    }

    protected QuestionServerSideWithPhoto createQuestionWithPhoto() {
        int randomNumberOfFile = new RandomNumberWithRange().randomInteger(1, numberOfQuestions());


        if (randomNumberOfFile < 1) try {
            throw new EmptyQuestionFolderException("Not found any files in: " + path);
        } catch (EmptyQuestionFolderException emptyQuestionFolder) {
            emptyQuestionFolder.printStackTrace();
        }

        return new QuestionServerSideWithPhoto(randomNumberOfFile);
    }
}
