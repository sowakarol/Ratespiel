package pl.edu.agh.kis.utils;

import pl.edu.agh.kis.Exception.InvalidRangeException;

import java.util.Random;

/**
 * Created by Karl on 07.01.2017.
 * Class with methods getting a random number
 */
public class RandomNumberWithRange {
    /**
     * random class to pseudo-random get numbers
     */
    private Random random;

    /**
     * constructor creating new Random class
     */
    public RandomNumberWithRange() {
        random = new Random();
    }

    /**
     * @param seed constructor with constant seed
     */
    public RandomNumberWithRange(int seed) {
        random = new Random(seed);
    }

    /**
     * @param from from a number
     * @param to   to a number
     * @return number in given range
     * @throws InvalidRangeException if given range is not valid
     */
    public int randomInteger(int from, int to) throws InvalidRangeException {
        if (to < from)
            throw new InvalidRangeException();

        int divider = to - from + 1;

        int ret = random.nextInt(Integer.MAX_VALUE) % divider;


        return ret + from;
    }


}
