package pl.edu.agh.kis;

import pl.edu.agh.kis.Exception.InvalidRangeException;

import java.util.Random;

/**
 * Created by Karl on 07.01.2017.
 */
public class RandomNumberWithRange {
    Random random;

    RandomNumberWithRange() {
        random = new Random();
    }

    RandomNumberWithRange(int seed) {
        random = new Random(seed);
    }

    public int randomInteger(int from, int to) {
        if (to < from) try {
            throw new InvalidRangeException();
        } catch (InvalidRangeException invalidRange) {
            invalidRange.printStackTrace();
        }


        System.out.println("from: " + from + " to: " + to);
        int divider = to - from + 1;
        int ret = Math.abs(random.nextInt()) % divider;
        return ret + from;
    }


}
