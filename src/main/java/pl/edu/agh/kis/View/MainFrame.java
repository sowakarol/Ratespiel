package pl.edu.agh.kis.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by Karl on 11.01.2017.
 */
public class MainFrame extends JFrame {
    private static String title = "Ratespiele";
    private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\View\\";
    boolean changeDefaultToGame = false;
    DefaultActionListener defaultActionListener = new DefaultActionListener();


    MainFrame() {
        super(title);
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setDefault();

    }

    void setDefault() { //menu
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //try {
        //    setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(path + "background.jpg")))));
        //} catch (IOException e) {
        //    e.printStackTrace();
        //getContentPane().setBackground(Color.GREEN);
        //}
        pack();

        JPanel panel = new JPanel();

        add(panel);
        setDefaultButtons(defaultActionListener, panel);

        setVisible(true);
    }

    void setDefaultButtons(ActionListener actionListener, JPanel panel) {
        JButton b1 = new JButton("start");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        b1.setMnemonic(KeyEvent.VK_D);
        b1.setActionCommand("disable");


        //Listen for actions on buttons 1 and 3.
        b1.addActionListener(actionListener);

        b1.setToolTipText("Start");
        panel.add(b1);
    }

    class DefaultActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeDefaultToGame = true;
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.set();


        }
    }
}
