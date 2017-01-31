package pl.edu.agh.kis.Model.question;

import java.io.*;

/**
 * Created by Karl on 06.01.2017.
 * Class representing question on server side
 */
public class QuestionServerSide extends QuestionServerSideAbstract {
    private String toTranslate;


    /**
     * @param id   number of question in directory from questions
     * @param path path to directory with questions
     */
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
            //BufferedReader bufferedReader = new BufferedReader(new FileReader(questionFile));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(questionFile), "UTF-8"));
            toTranslate = bufferedReader.readLine();

            for (int i = 0; i < 4; i++) {
                answers.add(i, bufferedReader.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return question's id
     */
    public int getQuestionNumber() {
        return questionNumber;
    }

    /**
     * @return question string
     */
    public String getToTranslate() {
        return toTranslate;
    }

}
