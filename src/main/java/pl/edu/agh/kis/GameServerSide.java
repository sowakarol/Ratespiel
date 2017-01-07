package pl.edu.agh.kis;

import pl.edu.agh.kis.Exception.EmptyQuestionFolderException;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.ServerSocket;

/**
 * Created by Karl on 07.01.2017.
 */
public abstract class GameServerSide implements GameServerSideInterface {
    private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\";


    public void play() {
        /*while(!isOver()){
            playRound();
        }*/
    }


    public void playRound() {
        QuestionServerSide question = createQuestion();
        sendQuestionToPlayer(question);

    }


    private void sendQuestionToPlayer(QuestionServerSide questionServerSide) {
        QuestionClientSide questionToSend = new QuestionClientSide(questionServerSide.answers, questionServerSide.toTranslate);


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
