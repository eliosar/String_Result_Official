package com.github.e.borho.swingcalculator;

import javax.swing.JTextField;

public class InputTextField extends JTextField {

    public InputTextField(InputConsumer inputConsumer){
        addActionListener(e -> {
            inputConsumer.enterPressed(getText());
        });
    }
}
