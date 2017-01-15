package pl.edu.agh.kis.Model;

/**
 * Created by Karl on 15.01.2017.
 */
public class QuestionServerSideAbstract extends Question {
    private int questionNumber;


    public QuestionServerSideAbstract(int id) {
        questionNumber = id;

    }

    public int getQuestionNumber() {
        return questionNumber;
    }

}
