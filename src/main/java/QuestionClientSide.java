import java.util.Vector;

/**
 * Created by Karl on 06.01.2017.
 */
public class QuestionClientSide extends Question {
    Vector<Integer> randomSortedAnswers = new Vector<>();


    QuestionClientSide(int id) {
        super(id);
    }
}
