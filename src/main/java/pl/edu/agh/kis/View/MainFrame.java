package pl.edu.agh.kis.View;

import pl.edu.agh.kis.Controller.MainController;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by Karl on 11.01.2017.
 */
public class MainFrame extends JFrame {
    private static String title = "Ratespiele";
    private final String path = "C:\\Users\\Karl\\GIT\\Ratespiel\\src\\main\\resources\\View\\";
    boolean changeDefaultToGame = false;
    MainController mainController;
    private JPanel mainPanel;

    public MainFrame(MainController mainController) {
        super(title);
        this.mainController = mainController;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    /*public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setDefault();

    }*/

    public void setDefault() { //menu
        //try {
        //setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(path + "background.jpg")))));
        //setIconImage(new ImageIcon(path + "icon.jpg").getImage());

        // } catch (IOException e) {
        //     e.printStackTrace();
        //getContentPane().setBackground(Color.GREEN);
        // }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        setDefaultButtons(mainController, mainPanel);
        add(mainPanel);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);

        pack();
        setSize(500, 500);
        setVisible(true);
    }

    private void setDefaultButtons(ActionListener actionListener, JPanel panel) {
        JButton b1 = new JButton("start");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        b1.setActionCommand("disable");


        //Listen for actions on buttons 1 and 3.
        b1.addActionListener(actionListener);

        //b1.setToolTipText("Start");
        panel.add(b1);
        panel.setVisible(true);
        pack();
    }

/*    class DefaultActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeDefaultToGame = true;
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.set();


        }
    }*/
}
