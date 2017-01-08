package pl.edu.agh.kis;

import pl.edu.agh.kis.Exception.EmptyQuestionFolderException;

import java.io.File;
import java.util.Vector;

/**
 * Created by Karl on 07.01.2017.
 */
public abstract class GameServerSide implements GameServerSideInterface {
    private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\";
    PlayerServerSide player1;
    PlayerServerSide player2;

    GameServerSide(PlayerServerSide player1, PlayerServerSide player2) {
        this.player1 = player1;
        this.player2 = player2;
    }
/* TODO
* chooseWinner
* multithreading
* Vector of players
* play()
*/

    GameServerSide(PlayerServerSide player1) {
        this.player1 = player1;
    }


    public void play() {
        playRound();
        /*while(!isOver()){
            playRound();
        }*/
    }


    public void playRound() { // WITHOUT MULTITHREAD
        QuestionServerSide question = createQuestion();

        sendQuestionToPlayer(question, player1);
        //sendQuestionToPlayer(question, player2);
        Vector<Answer> answers = new Vector<>(1); //initialCapacity zależne od playersNumber
        answers.add(getAnswer(player1));
        System.out.println("NIGGA WE MADE IT");
        //answers.add(getAnswer(player2));

        chooseWinner(answers, question);



    }

    private int chooseWinner(Vector<Answer> answers, QuestionServerSide question) {
        Vector<Answer> correctAnswers = new Vector<>();
        for (Answer answer : answers
                ) {
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
