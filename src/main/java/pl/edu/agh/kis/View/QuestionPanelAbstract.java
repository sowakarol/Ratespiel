package pl.edu.agh.kis.View;

import pl.edu.agh.kis.Controller.QuestionControllerAbstract;
import pl.edu.agh.kis.Model.question.QuestionClientSideAbstract;
import pl.edu.agh.kis.panels.HowManyRoundsLeftPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Karl on 15.01.2017.
 */
public abstract class QuestionPanelAbstract extends JPanel {
    protected QuestionClientSideAbstract question;
    protected MainFrame mainFrame;
    protected QuestionControllerAbstract questionController;
    protected Container container;
    protected int howManyLoopsLeft;


    public QuestionPanelAbstract(QuestionClientSideAbstract question, MainFrame mainFrame,
                                 QuestionControllerAbstract questionController, int howManyLoopsLeft) {
        super();
        this.question = question;
        this.mainFrame = mainFrame;
        this.questionController = questionController;
        this.howManyLoopsLeft = howManyLoopsLeft;
    }

    protected void addAnswersAndPrepare() {
        JPanel answersButtonsPanel = new JPanel();
        //add(new HowManyRoundsLeftPanel(howManyLoopsLeft));
        answersButtonsPanel.setLayout(new BoxLayout(answersButtonsPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < 4; i++) {
            JButton button = new JButton(question.getAnswers().get(i));
            button.addActionListener(questionController);
            button.setActionCommand(question.getAnswers().get(i));

            answersButtonsPanel.add(button);
            answersButtonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        add(answersButtonsPanel);
        setVisible(true);

        add(new HowManyRoundsLeftPanel(howManyLoopsLeft));

        //if(mainFrame.getMainPanel() == null) System.out.println("PRZYPA");
        container = mainFrame.getContentPane();
        container.removeAll();
        //mainFrame.revalidate();

        //mainFrame.repaint();
        //mainFrame.revalidate();
        //loginController.getLoginPanel().set();
        container.add(this);
        //mainFrame.repaint();
        //mainFrame.revalidate();
        mainFrame.validate();
        mainFrame.repaint();
        mainFrame.setVisible(true);

    }


}
