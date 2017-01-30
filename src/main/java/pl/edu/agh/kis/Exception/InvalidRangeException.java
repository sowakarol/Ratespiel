package pl.edu.agh.kis.Exception;

/**
 * Created by Karl on 07.01.2017.
 * Exception used in class RandomNumberWithRange
 * Representing a number that doesn't include in wanted range
 */
public class InvalidRangeException extends RatespielException {
    /**
     * constructor of this exception
     */
    public InvalidRangeException() {
        super();
    }

    /**
     * @param msg a String message
     */
    public InvalidRangeException(String msg) {
        super(msg);
    }
}
