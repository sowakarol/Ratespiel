package pl.edu.agh.kis;

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
        System.out.println("from: " + from + " to: " + to);
        int divider = to - from + 1;
        int ret = Math.abs(random.nextInt()) % divider;
        return ret + from;
    }
}
