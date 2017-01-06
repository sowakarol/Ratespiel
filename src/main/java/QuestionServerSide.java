import java.io.*;

/**
 * Created by Karl on 06.01.2017.
 */
public class QuestionServerSide extends Question {
    private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\";
    int questionNumber;

    QuestionServerSide(int id) {
        super(id);
    }

    boolean IsTrue(String answer) {
        File questionFile = new File(path + this.questionNumber);
        if (questionFile.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(questionFile));
                bufferedReader.readLine();
                String trueQuestion = bufferedReader.readLine();

                if (trueQuestion.equals(answer)) {
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
