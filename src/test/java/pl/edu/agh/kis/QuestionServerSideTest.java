package pl.edu.agh.kis;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Karl on 06.01.2017.
 */
class QuestionServerSideTest {
    @Test
    public void setQuestionAndAssertItsCorrect() {
        int testID = 1;
        final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\";
        QuestionServerSide testQuestion = null;
        try {
            testQuestion = new QuestionServerSide(testID);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(testID, testQuestion.questionNumber);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path + testID));
            assertEquals(bufferedReader.readLine(), testQuestion.toTranslate);

            for (int i = 0; i < 4; i++) {
                assertEquals(bufferedReader.readLine(), testQuestion.answers.get(i));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkExceptionIfQuestionFileIsNotExisting() {
        int testID = -1;
        String expected = "java.io.FileNotFoundException: C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\-1 (The system cannot find the file specified)";

        try {
            QuestionServerSide testQuestion = new QuestionServerSide(testID);
            //fail("Expected FileNotFoundException");
        } catch (FileNotFoundException e) {
            assertEquals(expected, e.getMessage());
        }
    }


}