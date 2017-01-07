package pl.edu.agh.kis;

import java.io.*;
import java.util.Vector;

/**
 * Created by Karl on 06.01.2017.
 */
public abstract class Question {
    private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\";
    Vector<String> answers = new Vector<>(4);

    public Question(Vector<String> answers) {
        this.answers = answers;
    }

    public Question() {
    }

}
