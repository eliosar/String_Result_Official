package e.borho.swingcalculator;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    
    public static List<Lexeme> parse(String input){
        List<Lexeme> lexemes = new ArrayList<>();
        StringBuilder currentExpression = new StringBuilder();
        for(char c: input.toCharArray()) {
            if(isNumberPart(c)){
                currentExpression.append(c);
            }

            if(isPlusMinus(""+c)){
                if(currentExpression.length() > 0){
                    //dann muss es sich um einen Operator handeln;
                    lexemes.add(createLexeme(currentExpression.toString()));
                    currentExpression = new StringBuilder();
                    Lexeme lexeme = createLexeme(""+c);
                    lexemes.add(lexeme);
                    continue;
                }else{
                    currentExpression.append(c);
                }
            }
            if(isOperatorOtherThanPlusMinus(""+c)){
                if(currentExpression.length() > 0){
                    //dann muss es sich um einen Operator handeln;
                    lexemes.add(createLexeme(currentExpression.toString()));
                    currentExpression = new StringBuilder();
                }
                Lexeme lexeme = createLexeme(""+c);
                lexemes.add(lexeme);
                continue;
            }

            if(isParenthesis(c)){
                if(currentExpression.length() > 0){
                    lexemes.add(createLexeme(currentExpression.toString()));
                    currentExpression = new StringBuilder();
                }
                Lexeme lexeme = createLexeme(""+c);
                lexemes.add(lexeme);
            }
        }

        //die letzten sachen hinzufügen
        if(currentExpression.length() > 0){
            lexemes.add(createLexeme(currentExpression.toString()));
        }
        
        return lexemes;
    }

    private static boolean isParenthesis(char charAt) {
        return charAt == '(' || charAt == ')';
    }

    private static boolean isLexemeTerminated(char c, char p) {
        if(isNumberPart(c)){

        }
        return !isNumberPart(c);
    }

    private static boolean isNumberPart(char c) {
        return c == '.'
                || c == 'e'
                || c == '0'
                || c == '1'
                || c == '2'
                || c == '3'
                || c == '4'
                || c == '5'
                || c == '6'
                || c == '7'
                || c == '8'
                || c == '9';
    }

    private static Lexeme createLexeme(String expression) {
        if(isNumber(expression)){
            return new Lexeme(expression, Lexeme.Type.NUMBER);
        }
        if(isOperatorOtherThanPlusMinus(expression)){
            return new Lexeme(expression, Lexeme.Type.OPERATOR);
        }
        if(isPlusMinus(expression)){
            return new Lexeme(expression, Lexeme.Type.OPERATOR);
        }
        if(isLeftParenthesis(expression)){
            return new Lexeme(expression, Lexeme.Type.LEFT_PARENTHESIS);
        }
        if(isRightParenthesis(expression)){
            return new Lexeme(expression, Lexeme.Type.RIGHT_PARENTHESIS);
        }
        throw new IllegalArgumentException("cannot lexe '"+expression+"'");
    }

    private static boolean isNumber(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperatorOtherThanPlusMinus(String str) {
        return "*".equals(str) || "/".equals(str) || "^".equals(str);
    }

    private static boolean isLeftParenthesis(String charAt) {
        return charAt.equals("(");
    }

    private static boolean isRightParenthesis(String charAt) {
        return charAt.equals(")");
    }


    private static boolean isPlusMinus(String c ) {
        return c.equals("-") ||  c.equals("+");
    }

    private static boolean isWhiteSpace(char c) {
        return ' ' == c;
    }
}
