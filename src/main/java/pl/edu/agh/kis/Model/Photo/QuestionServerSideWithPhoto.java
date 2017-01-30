package pl.edu.agh.kis.Model.Photo;

import pl.edu.agh.kis.Model.question.QuestionServerSideAbstract;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Karl on 15.01.2017.
 * Class representing question from a server side
 */
public class QuestionServerSideWithPhoto extends QuestionServerSideAbstract {
    /**
     * variable representing image
     */
    private BufferedImage image;
    /**
     * variable representing file in which qustion is saved
     */
    private File fileImage;


    /**
     * @param id   number of question
     * @param path path to a directory in which question is saved
     */
    public QuestionServerSideWithPhoto(int id, String path) {
        super(id);
        try {
            fileImage = new File(path + id + ".jpg");
            image = ImageIO.read(fileImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File questionFile = new File(path + id);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(questionFile), "UTF-8"));

            for (int i = 0; i < 4; i++) {
                answers.add(i, bufferedReader.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return file in which image is saved
     */
    public File getFileImage() {
        return fileImage;
    }

    /**
     * @return number of question
     */
    public int getQuestionNumber() {
        return questionNumber;
    }

    /**
     * @return image of question
     */
    public BufferedImage getImage() {
        return image;
    }
}
