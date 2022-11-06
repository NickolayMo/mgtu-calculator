package common;

import common.utils.Utils;

import java.text.ParseException;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;


public class ExpressionCalculator {
    // temporary stack that holds operators, functions and brackets
    private final Stack<String> stackOperations = new Stack<>();
    // stack for holding expression converted to reversed polish notation
    private final Stack<String> stackRPN = new Stack<>();
    // stack for holding the calculations result
    private final Stack<String> stackAnswer = new Stack<>();



    private byte getPrecedence(String token) {
        if (token.equals("+") || token.equals("-")) {
            return 1;
        }
        return 2;
    }
    public void parse(String expression) throws ParseException {
        stackOperations.clear();
        stackRPN.clear();
        expression = expression.replace(" ", "").replace("(-", "(0-")
                .replace(",-", ",0-");
        if (expression.charAt(0) == '-') {
            expression = "0" + expression;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(expression,
                Utils.OPERATORS + Utils.SEPARATOR + "()", true);
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            if (Utils.isSeparator(token)) {
                while (!stackOperations.empty()
                        && !Utils.isOpenBracket(stackOperations.lastElement())) {
                    stackRPN.push(stackOperations.pop());
                }
            } else if (Utils.isOpenBracket(token)) {
                stackOperations.push(token);
            } else if (Utils.isCloseBracket(token)) {
                while (!stackOperations.empty()
                        && !Utils.isOpenBracket(stackOperations.lastElement())) {
                    stackRPN.push(stackOperations.pop());
                }
                stackOperations.pop();
                if (!stackOperations.empty()
                        && Utils.isFunction(stackOperations.lastElement())) {
                    stackRPN.push(stackOperations.pop());
                }
            } else if (Utils.isNumber(token)) {
                    stackRPN.push(token);
            } else if (Utils.isOperator(token)) {
                while (!stackOperations.empty()
                        && Utils.isOperator(stackOperations.lastElement())
                        && getPrecedence(token) <= getPrecedence(stackOperations
                        .lastElement())) {
                    stackRPN.push(stackOperations.pop());
                }
                stackOperations.push(token);
            } else if (Utils.isFunction(token)) {
                stackOperations.push(token);
            }
        }
        while (!stackOperations.empty()) {
            stackRPN.push(stackOperations.pop());
        }
        Collections.reverse(stackRPN);
    }
    public String calc(){
        do {
            String token = stackRPN.pop();
            if(Utils.isNumber(token)) {
                stackAnswer.push(token);
            }
            if(Utils.isOperator(token)) {
                String second = stackAnswer.pop();
                String first = stackAnswer.pop();
                if (token.equals("+")) {
                    double res = Double.parseDouble(first) + Double.parseDouble(second);
                    stackAnswer.push(Double.toString(res));
                }
                if (token.equals("-")) {
                    double res = Double.parseDouble(first) - Double.parseDouble(second);
                    stackAnswer.push(Double.toString(res));
                }
                if (token.equals("*")) {
                    double res = Double.parseDouble(first) * Double.parseDouble(second);
                    stackAnswer.push(Double.toString(res));
                }
                if (token.equals("/")) {
                    double res = Double.parseDouble(first) / Double.parseDouble(second);
                    stackAnswer.push(Double.toString(res));
                }
                if (token.equals("^")) {
                    double res = Math.pow(Double.parseDouble(first),Double.parseDouble(second));
                    stackAnswer.push(Double.toString(res));
                }
            }
            if(Utils.isFunction(token)) {
                String first = stackAnswer.pop();
                if (token.equals("cos")) {
                    double res = Math.cos(Double.parseDouble(first));
                    stackAnswer.push(Double.toString(res));
                }
                if (token.equals("sin")) {
                    double res = Math.sin(Double.parseDouble(first));
                    stackAnswer.push(Double.toString(res));
                }
                if (token.equals("tan")) {
                    double res = Math.tan(Double.parseDouble(first));
                    stackAnswer.push(Double.toString(res));
                }
                if (token.equals("tanh")) {
                    double res = Math.tanh(Double.parseDouble(first));
                    stackAnswer.push(Double.toString(res));
                }
            }
        } while (!stackRPN.isEmpty());
        return stackAnswer.pop();
    }
}
