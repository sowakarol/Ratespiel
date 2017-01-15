package pl.edu.agh.kis.View;

import pl.edu.agh.kis.Controller.QuestionController;
import pl.edu.agh.kis.Model.QuestionClientSideAbstract;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Karl on 15.01.2017.
 */
public class QuestionPanelAbstract extends JPanel {
    protected QuestionClientSideAbstract question;
    protected MainFrame mainFrame;
    protected QuestionController questionController;
    protected Container container;


    public QuestionPanelAbstract(QuestionClientSideAbstract question, MainFrame mainFrame, QuestionController questionController) {
        super();
        this.question = question;
        this.mainFrame = mainFrame;
        this.questionController = questionController;
    }

    protected void addAnswersAndPrepare() {
        JPanel answersButtonsPanel = new JPanel();
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
        System.out.println("ASDAS");
        mainFrame.validate();
        mainFrame.repaint();
        mainFrame.setVisible(true);
        System.out.println("ASDAS");

    }


}
