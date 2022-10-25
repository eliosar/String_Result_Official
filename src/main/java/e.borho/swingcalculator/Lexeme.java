package e.borho.swingcalculator;

public class Lexeme {

    //well - Lexeme is another word for 'input token' - but why easy if you can do hard

    public enum Type {LEFT_PARENTHESIS, RIGHT_PARENTHESIS, OPERATOR, FUNCTION, NUMBER}

    private final String value;
    private final Type type;

    public Lexeme(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    public boolean isNumber() {
        return type == Type.NUMBER;
    }

    public boolean isLeftParenthesis() {
        return type == Type.LEFT_PARENTHESIS;
    }

    public boolean isRightParenthesis() {
        return type == Type.RIGHT_PARENTHESIS;
    }

    public int getPrecedence() {
        try {
            Operator operator = Operator.getBySign(value);
            return operator.precedence;
        }catch (IllegalArgumentException e){
            return -1;
        }
    }

    public boolean hasLeftAssociativity() {
        try {
            Operator operator = Operator.getBySign(value);
            return !operator.isRightAssociativity;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    public boolean isOperator() {
        return type == Type.OPERATOR;
    }

    public Operator getOperator() {
        return Operator.getBySign(value);
    }

    public double asDouble() {
        if(type != Type.NUMBER){
            throw new IllegalStateException("this method is only Tokens of Type NUMBER allowed");
        }
        return Double.parseDouble(value);
    }




    @Override
    public String toString() {
        return "'" + value + "'";
    }

}
