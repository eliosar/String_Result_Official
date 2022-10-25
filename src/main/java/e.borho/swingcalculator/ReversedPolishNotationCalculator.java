package e.borho.swingcalculator;

import java.util.List;
import java.util.Stack;

public class ReversedPolishNotationCalculator {

    public static Double calculate(List<Lexeme> tokens){
        Stack<Lexeme> stack = new Stack<>();
        for(Lexeme token: tokens){
            if(token.isOperator()){
                Lexeme a = stack.pop();
                Lexeme b = stack.pop();
                Lexeme result = compute(b,a,token);
                stack.push(result);
            }else{
                stack.push(token);
            }
        }
        return stack.pop().asDouble();
    }

    private static Lexeme compute(Lexeme firstNumberToken, Lexeme secondNumberToken, Lexeme operatorToken){
        Operator operator = operatorToken.getOperator();
        double firstDouble = firstNumberToken.asDouble();
        double secondDouble = secondNumberToken.asDouble();
        switch (operator){
            case PLUS: return new Lexeme(""+(firstDouble + secondDouble), Lexeme.Type.NUMBER);
            case MINUS:return new Lexeme(""+(firstDouble - secondDouble), Lexeme.Type.NUMBER);
            case MULTIPLY: return new Lexeme(""+(firstDouble * secondDouble), Lexeme.Type.NUMBER);
            case DIVIDE: return new Lexeme(""+(firstDouble / secondDouble), Lexeme.Type.NUMBER);
            case POWER: return new Lexeme(""+Math.pow(firstDouble, secondDouble), Lexeme.Type.NUMBER);
            default:throw new IllegalArgumentException("illegal input");
        }
    }
}
