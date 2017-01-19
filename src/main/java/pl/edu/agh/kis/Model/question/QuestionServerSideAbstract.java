package pl.edu.agh.kis.Model.question;

/**
 * Created by Karl on 15.01.2017.
 */
public class QuestionServerSideAbstract extends Question {
    protected int questionNumber;


    public QuestionServerSideAbstract(int id) {
        questionNumber = id;

    }

    public int getQuestionNumber() {
        return questionNumber;
    }

}
