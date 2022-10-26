package com.github.e.borho.swingcalculator;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LexerTest {

    @Test
    public void test_doubleMinus_isValid(){
        //given
        String input = " - 3 - - 4";

        //when
        List<Lexeme> tokens = Lexer.parse(input);

        //then
        Assert.assertTrue(tokens.get(0).isNumber());
        Assert.assertEquals(tokens.get(0).asDouble(), -3d, 0.001d);

        Assert.assertTrue(tokens.get(1).isOperator());
        Assert.assertEquals(Operator.MINUS, tokens.get(1).getOperator());

        Assert.assertTrue(tokens.get(2).isNumber());
        Assert.assertEquals(tokens.get(2).asDouble(), -4d, 0.001d);
    }

}
