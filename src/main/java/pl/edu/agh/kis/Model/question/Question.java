package pl.edu.agh.kis.Model.question;

import java.util.ArrayList;

/**
 * Created by Karl on 06.01.2017.
 */
public abstract class Question {
    //private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\";
    protected ArrayList<String> answers = new ArrayList<>(4);

    public Question(ArrayList<String> answers) {
        this.answers = answers;
    }

    public Question() {

    }

    public ArrayList<String> getAnswers() {

        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

}
