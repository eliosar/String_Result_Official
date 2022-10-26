package com.github.e.borho.swingcalculator;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

public class Main {

    private final JFrame frame = new JFrame();
    private final JPanel mainPanel = new JPanel();
    private final Dimension size = new Dimension(480, 90);
    private final Font font = new Font("Arial", Font.BOLD, 50);
    private final JTextField resultText = new JTextField();

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setBackground(Color.GRAY);
 
        InputConsumer inputConsumer = this::handleEnterPressed;

        InputTextField calculationText = new InputTextField(inputConsumer);
        calculationText.setPreferredSize(size);
        calculationText.setFont(font);

        resultText.setPreferredSize(size);
        resultText.setFont(font);
        resultText.setEditable(false);

        panel.add(calculationText);
        panel.add(resultText);

        mainPanel.setBackground(Color.GRAY);
        mainPanel.add(panel);

        frame.setSize(1400, 1080);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void handleEnterPressed(String input) {
        List<Lexeme> lexemes = Lexer.parse(input);
        List<Lexeme> inReversedPolishNotation = ShuntingYardAlgorithm.orderIntoReversedPolishedNotation(lexemes);
        double result = ReversedPolishNotationCalculator.calculate(inReversedPolishNotation);
        resultText.setText(Double.toString(result));
    }


}
