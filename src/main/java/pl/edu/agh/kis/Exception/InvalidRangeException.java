package pl.edu.agh.kis.Exception;

/**
 * Created by Karl on 07.01.2017.
 */
public class InvalidRangeException extends RatespielException {
    public InvalidRangeException() {
        super();
    }

    public InvalidRangeException(String msg) {
        super(msg);
    }
}
