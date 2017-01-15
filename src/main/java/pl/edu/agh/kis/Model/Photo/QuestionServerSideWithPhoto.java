package pl.edu.agh.kis.Model.Photo;

import pl.edu.agh.kis.Model.QuestionServerSideAbstract;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Karl on 15.01.2017.
 */
public class QuestionServerSideWithPhoto extends QuestionServerSideAbstract {
    private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\QuestionsWithPhotos\\";
    private int questionNumber;
    private BufferedImage image;


    public QuestionServerSideWithPhoto(int id) {
        super(id);
        try {
            image = ImageIO.read(new File(path + id + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File questionFile = new File(path + id);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(questionFile));

            for (int i = 0; i < 4; i++) {
                answers.add(i, bufferedReader.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public BufferedImage getImage() {
        return image;
    }
}
