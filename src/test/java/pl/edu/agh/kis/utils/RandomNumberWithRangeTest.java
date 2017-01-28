package pl.edu.agh.kis.utils;

import org.junit.Test;
import pl.edu.agh.kis.Exception.InvalidRangeException;

/**
 * Created by Karl on 28.01.2017.
 */
public class RandomNumberWithRangeTest {

    @Test(expected = Exception.class)
    public void checkInvalidErrorException() throws InvalidRangeException {
        RandomNumberWithRange randomNumberWithRange = new RandomNumberWithRange();
        randomNumberWithRange.randomInteger(20, 1);
    }


}