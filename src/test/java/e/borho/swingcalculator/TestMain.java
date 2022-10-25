package e.borho.swingcalculator;

import java.util.Arrays;
import java.util.List;

public class TestMain {

    //test case
    public static void main(String[] args) {

        testLexer();
        testInputCalculator();
        testRPNC();

    }

    private static void testLexer() {
//        String input = "-3-(-.4e3)";
//        String input = "3 - - 4";
//        String input = " - 3 * - 4";
        String input = " - 3 - - 4";
        List<Lexeme> tokens = Lexer.parse(input);
        System.out.println("lexeme: " + tokens);
    }

    private static void testRPNC() {
        List<Lexeme> tokens = Arrays.asList(
                new Lexeme("3", Lexeme.Type.NUMBER),
                new Lexeme("5", Lexeme.Type.NUMBER),
                new Lexeme("2", Lexeme.Type.NUMBER),
                new Lexeme("*", Lexeme.Type.OPERATOR),
                new Lexeme("+", Lexeme.Type.OPERATOR));
        double result = ReversedPolishNotationCalculator.calculate(tokens);
        System.out.println("result: " + result);
    }

    private static void testInputCalculator() {
        //given
//        String input = "4 + 4 * 2 / ( 1 - 5 )";
//        String input = "5 + 2 / (3- 8) ^ 5 ^ 2";
//        String input = "5 ^ 2";
        String input = " - 3 - - 4";
        List<Lexeme> tokens = Lexer.parse(input);
        System.out.println(tokens);
        List<Lexeme> result = ShuntingYardAlgorithm.orderIntoReversedPolishedNotation(tokens);
        System.out.println("result" + result);
        double calc = ReversedPolishNotationCalculator.calculate(result);
        System.out.println("calc: "+calc);
    }


}
