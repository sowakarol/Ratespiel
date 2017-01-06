import java.io.*;
import java.util.Vector;

/**
 * Created by Karl on 06.01.2017.
 */
public abstract class Question {
    private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\";
    Vector<String> answers = new Vector<>(4);
    String toTranslate;


    Question(int id) {
        File questionFile = new File(path + id);
        if (questionFile.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(questionFile));
                toTranslate = bufferedReader.readLine();

                for (int i = 0; i < 4; i++) {
                    answers.add(i, bufferedReader.readLine());
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERROR"); // POPRAW
        }
    }


}
