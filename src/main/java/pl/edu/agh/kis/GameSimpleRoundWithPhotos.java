package pl.edu.agh.kis;

import pl.edu.agh.kis.Exception.EmptyQuestionFolderException;
import pl.edu.agh.kis.Model.Photo.QuestionClientSideWithPhoto;
import pl.edu.agh.kis.Model.Photo.QuestionServerSideWithPhoto;
import pl.edu.agh.kis.Model.PlayerServerSide;
import pl.edu.agh.kis.Model.QuestionServerSideAbstract;

import java.io.File;

/**
 * Created by Karl on 15.01.2017.
 */
public class GameSimpleRoundWithPhotos extends GameSimpleRoundAbstract {
    private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\QuestionsWithPhotos\\";

    public GameSimpleRoundWithPhotos(int waitingForPlayersAnswer, PlayerServerSide... players) {
        super(waitingForPlayersAnswer, players);
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

    @Override
    protected void sendQuestionToPlayer(QuestionServerSideAbstract questionServerSide, PlayerServerSide player) {
        QuestionServerSideWithPhoto question = (QuestionServerSideWithPhoto) questionServerSide;
        QuestionClientSideWithPhoto questionToSend = new QuestionClientSideWithPhoto(question);
        questionToSend.randomizeAnswers();
        player.sendQuestion(questionToSend);
    }


    protected int numberOfQuestions() {
        try {
            File[] listOfFiles = new File(path).listFiles();
            return listOfFiles.length / 2;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public void play() {
        for (int i = 0; i < 4; i++) {
            playRound();
        }
    }
}
