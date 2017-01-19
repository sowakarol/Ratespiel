package pl.edu.agh.kis.Model.Photo;

import pl.edu.agh.kis.Model.question.QuestionClientSideAbstract;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

/**
 * Created by Karl on 15.01.2017.
 */
public class QuestionClientSideWithPhoto extends QuestionClientSideAbstract {
    private BufferedImage image;
    private File imageFile;

    public QuestionClientSideWithPhoto(QuestionServerSideWithPhoto questionServerSideWithPhoto) {
        super(questionServerSideWithPhoto.getAnswers());
        imageFile = questionServerSideWithPhoto.getFileImage();
        this.image = questionServerSideWithPhoto.getImage();
    }

    public QuestionClientSideWithPhoto(Vector<String> answers, BufferedImage image) {
        super(answers);
        this.image = image;
    }

    public File getImageFile() {
        return imageFile;
    }

    public BufferedImage getImage() {
        return image;
    }


}
