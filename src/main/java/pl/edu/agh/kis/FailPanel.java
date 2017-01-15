package pl.edu.agh.kis;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Karl on 15.01.2017.
 */
public class FailPanel extends JPanel {
    private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\View\\";

    public FailPanel() {
        try {
            add(new JLabel(new ImageIcon(ImageIO.read(new File(path + "fail.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
