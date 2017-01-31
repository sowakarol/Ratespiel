package pl.edu.agh.kis.Model.question;

import java.util.ArrayList;

/**
 * Created by Karl on 06.01.2017.
 * Class representing question
 */
public abstract class Question {
    /**
     * list of all possible answers
     */
    protected ArrayList<String> answers = new ArrayList<String>(4);

    /**
     * @param answers constructor with ArrayList of all possible answers
     */
    public Question(ArrayList<String> answers) {
        this.answers = answers;
    }

    /**
     * Constructor of Question
     */
    public Question() {

    }

    /**
     * getter of all possible answer
     *
     * @return arrayList of all possible answers
     */
    public ArrayList<String> getAnswers() {

        return answers;
    }

    /**
     * setter of all possible answer
     * @param answers
     */
    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

}
