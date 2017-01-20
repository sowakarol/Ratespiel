package pl.edu.agh.kis.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Karl on 20.01.2017.
 */
public class RatespielGetPropertyValuesTest {
    @Test
    public void getTestValue() {
        RatespielGetPropertyValues ratespielGetPropertyValues = new RatespielGetPropertyValues();
        try {
            assertEquals("test", ratespielGetPropertyValues.getValue("test"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}