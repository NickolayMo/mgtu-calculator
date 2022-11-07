package calculator.Scientific;

import javax.swing.*;
import java.awt.*;

public class CalculatorForm {

    private JPanel mPanel;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a6Button;
    private JButton a5Button;
    private JButton a4Button;
    private JButton a9Button;
    private JButton a8Button;
    private JButton a7Button;
    private JButton button10;
    private JButton a0Button;
    private JButton button12;
    private JTextField textField1;
    private JTextField textField2;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton ACButton;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton x2Button;
    private JButton xYButton;
    private JButton sinButton;
    private JButton cosButton;
    private JButton tanButton;
    private JPanel winPanel;
    private JPanel resulPanel;
    private JPanel inPanel;
    private JPanel outPanel;
    private JPanel opPanel;
    private JPanel numPanel;
    private JPanel funPanel;

    public CalculatorForm() {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new CalculatorForm().mPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Калькулятор");
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

}
