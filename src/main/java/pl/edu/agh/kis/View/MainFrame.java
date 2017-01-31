package pl.edu.agh.kis.View;

import pl.edu.agh.kis.Controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Karl on 11.01.2017.
 * mainframe where all panels go
 */
public class MainFrame extends JFrame {
    /**
     * title of this frame
     */
    private static String title = "Ratespiele";
    /**
     * main controller of that frame
     */
    private MainController mainController;
    /**
     * main starting panel of game
     */
    private JPanel mainPanel;

    /**
     * @param mainController controller of that frame
     */
    public MainFrame(MainController mainController) {
        super(title);
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * setting default values of that frame and panel
     */
    public void setDefault() { //menu
        //try {
        //setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(path + "background.jpg")))));
        //setIconImage(new ImageIcon(path + "icon.jpg").getImage());

        // } catch (IOException e) {
        //     e.printStackTrace();
        //getContentPane().setBackground(Color.GREEN);
        // }
        setLayout(new GridLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        mainPanel = new JPanel();
        setDefaultButtons(mainController, mainPanel);
        add(mainPanel);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);

        pack();
        setSize(500, 500);
        setVisible(true);
    }

    /**
     * @param actionListener actionlistener which is responsible for start button reaction
     * @param panel          panel in which buttons are going to be added
     */
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

    /**
     * actualizing MainController state to send a message to a server about disconnecting and closing window
     */
    @Override
    public void dispose() {
        mainController.disconnectPlayerWhenClosingWindow();
        super.dispose();
    }

}
