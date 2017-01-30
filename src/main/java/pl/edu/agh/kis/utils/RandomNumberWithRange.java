package pl.edu.agh.kis.utils;

import pl.edu.agh.kis.Exception.InvalidRangeException;

import java.util.Random;

/**
 * Created by Karl on 07.01.2017.
 */
public class RandomNumberWithRange {
    Random random;

    public RandomNumberWithRange() {
        random = new Random();
    }

    public RandomNumberWithRange(int seed) {
        random = new Random(seed);
    }

    public int randomInteger(int from, int to) throws InvalidRangeException {
        if (to < from)
            throw new InvalidRangeException();


        int divider = to - from + 1;

        int ret = random.nextInt(Integer.MAX_VALUE) % divider;


        return ret + from;
    }


}
