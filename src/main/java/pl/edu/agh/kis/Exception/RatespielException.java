package pl.edu.agh.kis.Exception;

/**
 * Created by Karl on 07.01.2017.
 * Exceptions occuring in Ratespiel game
 */
public class RatespielException extends Exception {
    public RatespielException() {
        super();
    }

    public RatespielException(String msg) {
        super(msg);
    }
}
