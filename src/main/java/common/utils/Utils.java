package common.utils;

public class Utils {
    // list of available functions
    public static final String[] FUNCTIONS = {"abs", "acos", "arg", "asin", "atan", "conj", "cos", "cosh", "exp", "log", "neg", "pow", "real", "sin", "sinh", "sqrt", "tan", "tanh"};
    // list of available operators
    public static final String OPERATORS = "+-*/^";
    // separator of arguments
    public static final String SEPARATOR = ",";

    public static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isFunction(String token) {
        for (String item : FUNCTIONS) {
            if (item.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSeparator(String token) {
        return token.equals(SEPARATOR);
    }

    public static boolean isOpenBracket(String token) {
        return token.equals("(");
    }

    public static boolean isCloseBracket(String token) {
        return token.equals(")");
    }

    public static boolean isOperator(String token) {
        return OPERATORS.contains(token);
    }
}
