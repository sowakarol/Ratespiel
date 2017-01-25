package pl.edu.agh.kis.Model;

/**
 * Created by Karl on 11.01.2017.
 */
public class QuestionServerSideTest {
    /*@Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void setQuestionAndAssertItsCorrect() {
        int testID = 1;
        final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\";
        QuestionServerSide testQuestion = null;
        testQuestion = new QuestionServerSide(testID);

        assertEquals(testID, testQuestion.getQuestionNumber());

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path + testID));
            assertEquals(bufferedReader.readLine(), testQuestion.getToTranslate());

            for (int i = 0; i < 4; i++) {
                assertEquals(bufferedReader.readLine(), testQuestion.getAnswers().get(i));
            }

        } catch (FileNotFoundException e) {
            //zrób coś tu
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkExceptionIfQuestionFileIsNotExisting() {
        String expected = "java.io.FileNotFoundException: C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\-1 (The system cannot find the file specified)";

        exception.expect(FileNotFoundException.class);
        exception.expectMessage(containsString(expected));
        int testID = -1;


        QuestionServerSide testQuestion = new QuestionServerSide(testID);
        //fail("Expected FileNotFoundException");

    }*/

}