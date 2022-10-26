package com.github.e.borho.swingcalculator;

import java.util.Objects;

public class Lexeme {

    //well - Lexeme is another word for 'input token' - but why easy if you can do hard

    private enum Type {LEFT_PARENTHESIS, RIGHT_PARENTHESIS, OPERATOR, FUNCTION, NUMBER}

    private final String value;
    private final Type type;

    public Lexeme(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    public static Lexeme number(String value){
        return new Lexeme(value, Type.NUMBER);
    }

    public static Lexeme operator(String value){
        return new Lexeme(value, Type.OPERATOR);
    }

    public static Lexeme rightParenthesis(){
        return new Lexeme(")", Type.RIGHT_PARENTHESIS);
    }
    public static Lexeme leftParenthesis(){
        return new Lexeme("(", Type.LEFT_PARENTHESIS);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lexeme lexeme = (Lexeme) o;
        return value.equals(lexeme.value) && type == lexeme.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }
}
