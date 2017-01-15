package pl.edu.agh.kis.Model.Photo;

import pl.edu.agh.kis.Model.QuestionClientSideAbstract;

import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * Created by Karl on 15.01.2017.
 */
public class QuestionClientSideWithPhoto extends QuestionClientSideAbstract {
    private BufferedImage image;

    public QuestionClientSideWithPhoto(QuestionServerSideWithPhoto questionServerSideWithPhoto) {
        super(questionServerSideWithPhoto.getAnswers());
        this.image = questionServerSideWithPhoto.getImage();
    }

    public QuestionClientSideWithPhoto(Vector<String> answers, BufferedImage image) {
        super(answers);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }


}
