package src.e.borho.stringResultOfficial;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class Frame {
    private JFrame frame = new JFrame();
    private JPanel mainPanel = new JPanel();
    private Dimension size = new Dimension(480, 90);
    private Font font = new Font("Arial", Font.BOLD, 50);
    private char[] lastResult;
    private JTextField calculationText = new JTextField();
    private JTextField resultText = new JTextField();

    public Frame(){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setBackground(Color.GRAY);

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
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.add(mainPanel);
    }

    public void show(){
        frame.setVisible(true);
    }
    public void setResult(String s){
        resultText.setText(s);
    }
    public String getInput(){
        return calculationText.getText();
    }

    public void setUnEdible(){
        calculationText.setEditable(false);
    }

    public void setActionListenerToCurrentLine(ActionListener al){
        calculationText.addActionListener(al);
    }

    public char[] getLastResult(){
        return lastResult;
    }

    public void addNewCalculationField(){
        JPanel lastPanel = (JPanel) mainPanel.getComponent(mainPanel.getComponents().length - 1);
        JTextField textField = (JTextField) lastPanel.getComponent(1);
        lastResult = textField.getText().toCharArray();

        JPanel newPanel = new JPanel();
        newPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        newPanel.setBackground(Color.GRAY);

        calculationText = new JTextField();
        calculationText.setPreferredSize(size);
        calculationText.setFont(font);

        SwingUtilities.invokeLater(() -> calculationText.requestFocus());

        resultText = new JTextField();
        resultText.setPreferredSize(size);
        resultText.setFont(font);
        resultText.setFocusable(false);

        newPanel.add(calculationText);
        newPanel.add(resultText);

        mainPanel.add(newPanel, BorderLayout.SOUTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}