package com.github.e.borho.swingcalculator;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ReversedPolishNotationCalculatorTest {

    @Test
    public void test_multiplication(){
        //given
        List<Lexeme> tokens = Arrays.asList(
                Lexeme.number("3"),
                Lexeme.number("5"),
                Lexeme.operator("*"));

        //when
        double result = ReversedPolishNotationCalculator.calculate(tokens);

        //then
        Assert.assertEquals(15d, result, 0.00001);
    }
}
