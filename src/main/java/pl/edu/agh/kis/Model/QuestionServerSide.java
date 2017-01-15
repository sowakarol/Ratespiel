package pl.edu.agh.kis.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Karl on 06.01.2017.
 */
public class QuestionServerSide extends QuestionServerSideAbstract {
    private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\Questions\\";
    private int questionNumber;
    private String toTranslate;
    //TODO
    //add something in one exception

    public QuestionServerSide(int id) {
        super(id);
        File questionFile = new File(path + id);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(questionFile));
            toTranslate = bufferedReader.readLine();

            for (int i = 0; i < 4; i++) {
                answers.add(i, bufferedReader.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public String getToTranslate() {
        return toTranslate;
    }

}
