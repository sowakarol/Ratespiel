package pl.edu.agh.kis.Model;

import java.io.*;

/**
 * Created by Karl on 06.01.2017.
 */
public class QuestionServerSide extends Question {
    private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\";
    int questionNumber;
    String toTranslate;


    QuestionServerSide(int id) {
        questionNumber = id;
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

    boolean IsTrue(String answer) {
        File questionFile = new File(path + this.questionNumber);
        if (questionFile.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(questionFile));
                bufferedReader.readLine(); //skipping toTranslate string
                String trueAnswer = bufferedReader.readLine();

                if (trueAnswer.equals(answer)) {
                    return true;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR"); // POPRAW
        }
        return false;
    }
}
