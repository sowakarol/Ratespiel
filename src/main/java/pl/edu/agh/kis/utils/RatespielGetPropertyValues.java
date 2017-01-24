package pl.edu.agh.kis.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Karl on 20.01.2017.
 * class reading config.properties values
 */
public class RatespielGetPropertyValues {


    public static Integer getPlayersNumber() {
        RatespielGetPropertyValues ratespielGetPropertyValues = new RatespielGetPropertyValues();
        try {
            return Integer.parseInt(ratespielGetPropertyValues.getValue("numberOfPlayers"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 2;
    }

    public static Integer getPortNumber() {
        RatespielGetPropertyValues ratespielGetPropertyValues = new RatespielGetPropertyValues();
        try {
            return Integer.parseInt(ratespielGetPropertyValues.getValue("portNumber"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 8888;
    }

    public static String getGameType() {
        RatespielGetPropertyValues ratespielGetPropertyValues = new RatespielGetPropertyValues();
        try {
            return ratespielGetPropertyValues.getValue("gameType");
        } catch (IOException e) {
            e.printStackTrace();
            return "default";
        }
    }

    public static String getHostname() {
        RatespielGetPropertyValues ratespielGetPropertyValues = new RatespielGetPropertyValues();
        try {
            return ratespielGetPropertyValues.getValue("hostname");
        } catch (IOException e) {
            e.printStackTrace();
            return "localhost";
        }
    }

    public static String getPath() {
        RatespielGetPropertyValues ratespielGetPropertyValues = new RatespielGetPropertyValues();
        try {
            return ratespielGetPropertyValues.getValue("path");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Integer getWaitingTimeForNewGame() {
        RatespielGetPropertyValues ratespielGetPropertyValues = new RatespielGetPropertyValues();
        try {
            return Integer.parseInt(ratespielGetPropertyValues.getValue("waitingTimeForNewGame"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 30;
    }

    public static Integer getMaximalRespondTime() {
        RatespielGetPropertyValues ratespielGetPropertyValues = new RatespielGetPropertyValues();
        try {
            return Integer.parseInt(ratespielGetPropertyValues.getValue("maximalRespondTime"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 5;
    }

    public static Integer getroundsNumber() {
        RatespielGetPropertyValues ratespielGetPropertyValues = new RatespielGetPropertyValues();
        try {
            return Integer.parseInt(ratespielGetPropertyValues.getValue("roundsNumber"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 4;
    }

    /**
     * @param key String representing the key value
     * @return value corresponding to the key
     */
    public String getValue(String key) throws IOException {
        Properties prop = new Properties();
        String propFileName = "config.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            try {
                prop.load(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                inputStream.close();
            }
        }
        return prop.getProperty(key);
    }
}
