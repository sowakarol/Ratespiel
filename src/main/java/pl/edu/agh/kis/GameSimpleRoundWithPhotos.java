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

    protected QuestionServerSideAbstract createQuestion() {
        int randomNumberOfFile = new RandomNumberWithRange().randomInteger(1, numberOfQuestions());


        if (randomNumberOfFile < 1) try {
            throw new EmptyQuestionFolderException("Not found any files in: " + path);
        } catch (EmptyQuestionFolderException emptyQuestionFolder) {
            emptyQuestionFolder.printStackTrace();
        }

        return new QuestionServerSideWithPhoto(randomNumberOfFile);
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



    /*public void playRound() {
        QuestionServerSideWithPhoto question = createQuestionWithPhoto();

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
    }*/
}
