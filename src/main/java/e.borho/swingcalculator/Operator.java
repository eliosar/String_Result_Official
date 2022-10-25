package e.borho.swingcalculator;

public enum Operator {
    POWER("^", 3, true),
    MULTIPLY("*", 2, false),
    DIVIDE("/", 2, false),
    PLUS("+", 1, false),
    MINUS("-", 1, false);

    public final String sign;
    public final int precedence;
    public final boolean isRightAssociativity;

    Operator(String sign, int precedence, boolean isRightAssociativity) {
        this.sign = sign;
        this.precedence = precedence;
        this.isRightAssociativity = isRightAssociativity;
    }


    public static Operator getBySign(String sign) {
        switch (sign) {
            case "^":
                return POWER;
            case "*":
                return MULTIPLY;
            case "/":
                return DIVIDE;
            case "+":
                return PLUS;
            case "-":
                return MINUS;
            default:
                throw new IllegalArgumentException("no such operator '" + sign + "'");
        }
    }
}
