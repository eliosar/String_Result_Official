package e.borho.swingcalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ShuntingYardAlgorithm {

    //private List<InputToken> reversePolishNotatedResult = new ArrayList<>();// see https://de.wikipedia.org/wiki/Umgekehrte_polnische_Notation
    //https://stevepesce879.medium.com/rpn-calculator-in-java-a-practical-stack-implementation-2d93b660d630

    public static List<Lexeme> orderIntoReversedPolishedNotation(List<Lexeme> tokens) {
        List<Lexeme> result = new ArrayList<>();
//        List<InputToken> tokens = InputTokenizer.tokenize(input);

        //Shunting-yard-Algorithmus: https://de.wikipedia.org/wiki/Shunting-yard-Algorithmus
        //simplified Shunting-yard-Algorithmus: https://aquarchitect.github.io/swift-algorithm-club/Shunting%20Yard/
        //another simplified Shunting-yard-Algorithm: https://www.geeksforgeeks.org/java-program-to-implement-shunting-yard-algorithm/

        Stack<Lexeme> stack = new Stack<>();
        for (Lexeme token : tokens) {
            if (token.isNumber()) {
                result.add(token);
            }
            else if (token.isLeftParenthesis()) {
                stack.push(token);
            }
            else if (token.isRightParenthesis()) {
                while (!stack.isEmpty() && !stack.peek().isLeftParenthesis()) {
                    result.add(stack.pop());
                }
                stack.pop();
            }
            else {
                while (!stack.isEmpty() && token.getPrecedence() <= stack.peek().getPrecedence() && token.hasLeftAssociativity()) {
                    result.add(stack.pop());
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek().isRightParenthesis()) {
                throw new IllegalArgumentException("This expression is invalid");
            }
            result.add(stack.pop());

        }
        return result;
    }

}