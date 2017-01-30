package pl.edu.agh.kis.Model.Photo;

import pl.edu.agh.kis.Model.question.QuestionClientSideAbstract;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Karl on 15.01.2017.
 * Class representing Question with photo from a client side
 */
public class QuestionClientSideWithPhoto extends QuestionClientSideAbstract {
    /**
     * photo represented by BufferedImage
     */
    private BufferedImage image;
    /**
     * a file in which a photo is saved
     */
    private File imageFile;

    /**
     * @param questionServerSideWithPhoto Question from a server side
     */
    public QuestionClientSideWithPhoto(QuestionServerSideWithPhoto questionServerSideWithPhoto) {
        super(questionServerSideWithPhoto.getAnswers());
        imageFile = questionServerSideWithPhoto.getFileImage();
        this.image = questionServerSideWithPhoto.getImage();
    }

    /**
     * @param answers all possible answers to a question
     * @param image   image representing question
     */
    public QuestionClientSideWithPhoto(ArrayList<String> answers, BufferedImage image) {
        super(answers);
        this.image = image;
    }

    /** getter of image
     * @return image variable
     */
    public BufferedImage getImage() {
        return image;
    }


}
