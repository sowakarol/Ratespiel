package pl.edu.agh.kis.Exception;

/**
 * Created by Karl on 07.01.2017.
 */
public class EmptyQuestionFolderException extends InvalidRangeException {
    public EmptyQuestionFolderException() {
        super();
    }

    public EmptyQuestionFolderException(String msg) {
        super(msg);
    }
}
