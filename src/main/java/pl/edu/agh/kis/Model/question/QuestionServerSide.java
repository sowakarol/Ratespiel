package pl.edu.agh.kis.Model.question;

import java.io.*;

/**
 * Created by Karl on 06.01.2017.
 */
public class QuestionServerSide extends QuestionServerSideAbstract {
    //private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\Questions\\";
    private String toTranslate;
    //TODO
    //add something in one exception

    public QuestionServerSide(int id, String path) {
        super(id);
        File questionFile = new File(path + id);
        if (!questionFile.exists()) {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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
