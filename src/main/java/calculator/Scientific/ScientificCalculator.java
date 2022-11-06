package calculator.Scientific;

import calculator.Scientific.pannels.ButtonPanel;
import common.ExpressionCalculator;
import common.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.ParseException;
import java.util.*;


public class ScientificCalculator extends JFrame implements ActionListener {
    JTextField inField;
    JTextField outField;
    String currentExpression;
    Stack<String> currentExpressionTokens = new Stack<>();

    String[] scCalculatorNumpad = {
            "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "0",
            "+", "-", "*", "/","(",")","<-",
            "+/-", ".", "=", "x^2", "x^y","cos", "sin","tan","tanh",
            "C"
    };

    public ScientificCalculator() {
        Container cont = getContentPane();
        cont.setLayout(new BorderLayout());
        JPanel textpanel = new JPanel();
        textpanel.setLayout(new GridLayout(2, 25, 2, 2));
        JPanel resPanel = new JPanel();
        resPanel.setLayout(new GridLayout(1, 25, 2, 2));

        outField = new JTextField();
        outField.setHorizontalAlignment(SwingConstants.RIGHT);
        outField.setPreferredSize(new Dimension(100,50));
        outField.setFont(new Font("Arial", Font.PLAIN, 40));
        JLabel label = new JLabel();
        label.setText("Результат:");
        label.setPreferredSize(new Dimension(50,50));
        resPanel.add(label);
        resPanel.add(outField);

        inField = new JTextField();
        inField.setHorizontalAlignment(SwingConstants.RIGHT);
        inField.setPreferredSize(new Dimension(150,50));
        inField.setFont(new Font("Arial", Font.PLAIN, 40));
        textpanel.add(inField);
        textpanel.add(resPanel);
        ButtonPanel buttonpanel = new ButtonPanel(this);
        buttonpanel.setLayout(new GridLayout(8, 4, 2, 2));
        Arrays.stream(scCalculatorNumpad).forEach(buttonpanel::addButton);
        cont.add("Center", buttonpanel);
        cont.add("North", textpanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Калькулятор");
        this.setSize(800, 600);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        switch (s) {
            case "C" -> {
                inField.setText("");
                outField.setText("");
                currentExpressionTokens.clear();
                currentExpression = "";
            }
            case "=" -> {
                if (!outField.getText().isEmpty()) {
                    inField.setText(outField.getText());
                    outField.setText("");
                }
            }
            case "(", ")" -> {
                currentExpressionTokens.push(s);
                rewriteField();
            }
            case "<-" -> {
                if (!inField.getText().isEmpty()) {
                    inField.setText(inField.getText().substring(0, inField.getText().length()-1));
                }
                recalcField();
            }
            case "+/-" -> {
                if (!currentExpressionTokens.isEmpty()) {
                    String lastToken = currentExpressionTokens.pop();
                    if (Utils.isNumber(lastToken)) {
                        String op1 = "";
                        if (!currentExpressionTokens.isEmpty()) {
                            op1 = currentExpressionTokens.pop();
                        }
                        if (op1.equals("-")) {
                            String op2 = "";
                            if (!currentExpressionTokens.isEmpty()) {
                                op2 = currentExpressionTokens.pop();
                            }
                            if (op2.equals("(")) {
                                currentExpressionTokens.push(lastToken);
                            } else {
                                currentExpressionTokens.push(op1);
                                currentExpressionTokens.push(op2);
                                currentExpressionTokens.push("(");
                                currentExpressionTokens.push("-");
                                currentExpressionTokens.push(lastToken);
                            }
                        } else {
                            currentExpressionTokens.push(op1);
                            currentExpressionTokens.push("(");
                            currentExpressionTokens.push("-");
                            currentExpressionTokens.push(lastToken);
                        }
                    }
                    rewriteField();
                }
            }
            case "x^2" -> {
                if (!currentExpressionTokens.isEmpty()) {
                    String lastToken = currentExpressionTokens.pop();
                    if (Utils.isNumber(lastToken)) {
                        currentExpressionTokens.push(lastToken);
                        addFunc("^", "2");
                    }
                }
                rewriteField();
            }
            case "x^y" -> {
                if (!currentExpressionTokens.isEmpty()) {
                    String lastToken = currentExpressionTokens.pop();
                    if (Utils.isNumber(lastToken)) {
                        currentExpressionTokens.push(lastToken);
                        currentExpressionTokens.push("^");
                        currentExpressionTokens.push("(");
                    }
                }
                rewriteField();
            }
            case "cos", "sin", "tan", "tanh" -> {
                if (!currentExpressionTokens.isEmpty()) {
                    String lastToken = currentExpressionTokens.pop();
                    if (Utils.isNumber(lastToken)) {
                        addFunc(s, lastToken);
                    }
                }
                rewriteField();
            }
            default -> {
                inField.setText(inField.getText() + s);
                recalcField();
            }
        }
    }

    private void recalcField() {
        StringTokenizer stringTokenizer = new StringTokenizer(inField.getText(),
                Utils.OPERATORS + Utils.SEPARATOR + "()", true);
        currentExpressionTokens.clear();
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            currentExpressionTokens.add(token);
        }
        currentExpression = String.join("", currentExpressionTokens);
        ExpressionCalculator calculator = new ExpressionCalculator();
        try {
            if (currentExpression.length() > 0) {
                char last = currentExpression.charAt(currentExpression.length() - 1);
                if (Utils.isNumber(Character.toString(last)) || Character.toString(last).equals(")")) {
                    calculator.parse(currentExpression);
                    outField.setText(calculator.calc());
                }
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    private void rewriteField() {
        currentExpression = String.join("", currentExpressionTokens);
        inField.setText(currentExpression);
        recalcField();
    }

    private void addFunc(String funcName, String arg) {
        currentExpressionTokens.push(funcName);
        currentExpressionTokens.push("(");
        currentExpressionTokens.push(arg);
        currentExpressionTokens.push(")");
    }
}