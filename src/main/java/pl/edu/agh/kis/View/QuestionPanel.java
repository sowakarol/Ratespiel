package pl.edu.agh.kis.View;

import pl.edu.agh.kis.Controller.QuestionController;
import pl.edu.agh.kis.Model.QuestionClientSide;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Karl on 14.01.2017.
 */
public class QuestionPanel extends JPanel {
    QuestionClientSide question;
    MainFrame mainFrame;
    QuestionController questionController;
    private Container container;

    public QuestionPanel(QuestionClientSide question, MainFrame mainFrame, QuestionController questionController) {
        super();
        this.question = question;
        this.mainFrame = mainFrame;
        this.questionController = questionController;
        prepareAndShow();
    }

    public static void main(String[] args) throws InterruptedException {
        Vector<String> vector = new Vector<>();
        vector.add("aaa");
        vector.add("aba");
        vector.add("aca");
        vector.add("ada");


        QuestionClientSide questionClientSide = new QuestionClientSide(vector, "siemano");
        //MainFrame mainFrame = new MainFrame();
        //mainFrame.setDefault();
        //Thread.sleep(10000);
        // mainFrame.add()


        //JFrame frame = new JFrame();
        //QuestionPanel questionPanel = new QuestionPanel(questionClientSide);
        //questionPanel.prepareAndShow();
        //frame.add(questionPanel);
        //frame.pack();
        //frame.setVisible(true);

    }

    public void prepareAndShow() {
        System.out.println(":O");
        JPanel questionString = new JPanel(new BorderLayout());
        JTextPane textPane = new JTextPane();

        textPane.setContentType("text/html");
        textPane.setText(question.getToTranslate());

        textPane.setEditable(false);
        questionString.add(textPane);

        add(questionString);

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
