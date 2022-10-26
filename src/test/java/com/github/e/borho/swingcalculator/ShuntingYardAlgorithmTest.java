package com.github.e.borho.swingcalculator;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ShuntingYardAlgorithmTest {

    @Test
    public void test_sya(){
        //given
        List<Lexeme> tokens = Arrays.asList(
                Lexeme.number("3"),
                Lexeme.operator("-"),
                Lexeme.number("2"));

        //when
        List<Lexeme> result = ShuntingYardAlgorithm.orderIntoReversedPolishedNotation(tokens);

        //then
        Assert.assertEquals(tokens.get(0), result.get(0));
        Assert.assertEquals(tokens.get(2), result.get(1));
        Assert.assertEquals(tokens.get(1), result.get(2));
    }
}
