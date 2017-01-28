package pl.edu.agh.kis.utils;

import org.junit.Test;

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

    @Test(expected = Exception.class)
    public void getNullValue() {
        RatespielGetPropertyValues ratespielGetPropertyValues = new RatespielGetPropertyValues();
        try {
            assertEquals("test", ratespielGetPropertyValues.getValue(null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getWrongValue() {
        RatespielGetPropertyValues ratespielGetPropertyValues = new RatespielGetPropertyValues();
        try {
            assertEquals(null, ratespielGetPropertyValues.getValue("veryWrongValue"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}