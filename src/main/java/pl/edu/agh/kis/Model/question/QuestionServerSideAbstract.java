package pl.edu.agh.kis.Model.question;

/**
 * Created by Karl on 15.01.2017.
 * abstract class of question from server side
 */
public class QuestionServerSideAbstract extends Question {
    /**
     * id of question
     */
    protected int questionNumber;


    /**
     * @param id id representing question number
     */
    public QuestionServerSideAbstract(int id) {
        questionNumber = id;

    }

    /**
     * @return getter of question's id
     */
    public int getQuestionNumber() {
        return questionNumber;
    }

}
