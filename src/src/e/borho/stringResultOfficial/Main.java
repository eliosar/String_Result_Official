package src.e.borho.stringResultOfficial;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main {
    private static ArrayList<Character> mainInput = new ArrayList<>();

    private static int[] firstNumberIndexes = new int[2];
    private static int[] secondNumberIndexes = new int[2];

    private static int operatorIndex = -1;

    private static Calculator calculator = new Calculator();

    private static Frame frame;

    private static char[] result;

    public static void main(String[] args) {
        frame = new Frame();
        frame.setActionListenerToCurrentLine(actionEvent -> {
            setaction();
        });
        frame.show();
    }

    private static void setaction(){
        for (char c : frame.getInput().toCharArray()) {
            mainInput.add(c);
        }
        frame.setUnEdible();
        startCalc(mainInput);
        mainInput = new ArrayList<>();
        System.out.println(result);
        frame.setResult(String.valueOf(result));
        frame.addNewCalculationField();
        frame.setActionListenerToCurrentLine(actionEvent -> {
            setaction();
        });
    }

    private static void startCalc(ArrayList<Character> input){
        remodellInput(input);
        calculate(input);
    }

    private static void calculate(ArrayList<Character> input){ // calc (), calc * and /, calc + and -
        System.out.println("() started");
        while(calcBrackets(input)){
            remodellInput(input);}
        System.out.println("() finished");
        System.out.println("*/ started");
        while(calcMultiAndDivide(0, '*', '/', input)){
            remodellInput(input);}
        System.out.println("*/ finished");
        System.out.println("+- started");
        while(calcPlusAndMinus(1, '+', '-', input)){
            remodellInput(input);}
        System.out.println("+- finished");
    }

    private static boolean calcBrackets(ArrayList<Character> input){
        int openBracketIndex = -1;
        int closeBracketIndex = -1;

        for (int i = 0; i < input.size(); i++){
            char it = input.get(i);

            if(it == '('){
                openBracketIndex = i;
            }
            if(it == ')'){
                closeBracketIndex = i;
                break;
            }
        }

        if(openBracketIndex != -1) {
            if(closeBracketIndex == -1){
                setError("no close-bracket in calculation");
            }

            ArrayList<Character> innerInput = new ArrayList<>();
            for(int i = openBracketIndex + 1; i < closeBracketIndex; i++){
                innerInput.add(input.get(i));
            }

            if(isAlone(innerInput)){
                openBracketIndex = -1;
            }

            if(!isAlone(innerInput)){
                calculate(innerInput);
                firstNumberIndexes[0] = openBracketIndex + 1;
                secondNumberIndexes[1] = closeBracketIndex - 1;
                inputResult(input);
            }
        }

        return openBracketIndex != -1;
    }

    private static void setError(String error){
        frame.setUnEdible();
        frame.setResult("Error in calculation: " + error);
    }

    private static boolean isAlone(ArrayList<Character> input){
        for(int i = 0; i < input.size(); i++){
            if(checkOperatorsOr(input.get(i)) && i != 0){
                return false;
            }
        }
        return true;
    }

    private static boolean checkOperatorsOr(char input){
        return input == '+' || input == '-' || input == '*' || input == '/';
    }

    private static boolean checkOperatorsOrPoint(char input){
        return input == '*' || input == '/';
    }

    private static boolean checkOperatorsOrLine(char input){
        return input == '+' || input == '-';
    }

    private static boolean calcMultiAndDivide(int start, char operator1, char operator2, ArrayList<Character> input){
        return calc(start, operator1, operator2, input);
    }

    private static boolean calcPlusAndMinus(int start, char operator1, char operator2, ArrayList<Character> input){
        return calc(start, operator1, operator2, input);
    }

    private static boolean calc(int start, char operator1, char operator2, ArrayList<Character> input){
        operatorIndex = -1;

        setNewOperatorIndex(start, operator1, operator2, input);

        calcResult(input);

        return operatorIndex != -1;
    }

    private static void calcResult(ArrayList<Character> input){
        if(operatorIndex != -1) {
            try {
                float firstNumber = getFirstNumber(operatorIndex, input);
                float secondNumber = getSecondNumber(operatorIndex, input);

                if (input.get(operatorIndex) == '*') {
                    result = String.valueOf(calculator.multiplicate(firstNumber, secondNumber)).toCharArray();
                }
                if (input.get(operatorIndex) == '/') {
                    if(secondNumber == '0'){
                        setError("/0");
                    }

                    result = String.valueOf(calculator.divide(firstNumber, secondNumber)).toCharArray();
                }
                if (input.get(operatorIndex) == '+') {
                    result = String.valueOf(calculator.add(firstNumber, secondNumber)).toCharArray();
                }
                if (input.get(operatorIndex) == '-') {
                    result = String.valueOf(calculator.subtract(firstNumber, secondNumber)).toCharArray();
                }

                inputResult(input);
            }catch (Exception e){
                setError("non-number-input");
            }
        }
    }

    private static void setNewOperatorIndex(int start, char operator1, char operator2, ArrayList<Character> input){
        for(int i = start; i < input.size(); i++){
            if(input.get(i) == operator1 || input.get(i) == operator2){
                System.out.println("operator: " + input.get(i));
                operatorIndex = i;
                break;
            }
        }
    }

    private static void inputResult(ArrayList<Character> input){
        removeInputLine(input);

        int x = result.length;

        if(input.size() == 0){
            input.add(result[result.length - 1]);
            x = result.length - 1;
        }

        for(int i = 0; i < x; i++){
            input.add(firstNumberIndexes[0] + i, result[i]);
        }

        try {
            if (Float.parseFloat(String.valueOf(result)) >= 0) {
                input.add(firstNumberIndexes[0], '+');
            }
        }catch(Exception e){

        }
    }

    private static float getFirstNumber(int operatorIndex, ArrayList<Character> input){
        firstNumberIndexes[1] = operatorIndex - 1;
        System.out.println("1. Number");
        System.out.println("input: " + input);
        for(int i = operatorIndex - 1; i >= 0; i--){
            if(checkOperatorsOrLine(input.get(i)) || input.get(i) == '(' || (i > 0 && checkOperatorsOrPoint(input.get(i - 1)))){
                firstNumberIndexes[0] = i;
                break;
            }
            if(i == 0){
                firstNumberIndexes[0] = i;
                break;
            }
        }

        return getNumber(firstNumberIndexes[0], firstNumberIndexes[1], input);
    }

    private static float getSecondNumber(int operatorIndex, ArrayList<Character> input){
        secondNumberIndexes[0] = operatorIndex + 1;
        System.out.println("2. Number");
        System.out.println("input: " + input);
        for(int i = operatorIndex + 1; i < input.size(); i++){
            if(i == input.size() - 1){
                secondNumberIndexes[1] = i;
                break;
            }

            if((((checkOperatorsOrLine(input.get(i)) && input.get(i - 1) != '(')|| input.get(i) == ')')
                    && i != operatorIndex + 1)
                    || checkOperatorsOrPoint(input.get(i + 1))
                    || (checkOperatorsOrLine(input.get(i + 1)) && input.get(i) != '(')){
                secondNumberIndexes[1] = i;
                break;
            }
        }

        return getNumber(secondNumberIndexes[0], secondNumberIndexes[1], input);
    }

    private static float getNumber(int index1, int index2, ArrayList<Character> input){
        ArrayList<Character> number = new ArrayList<>();

        for(int i = index1; i <= index2; i++){
            if(input.get(i) != '(' && input.get(i) != ')') {
                number.add(input.get(i));
            }
        }

        System.out.println("index1: " + index1);
        System.out.println("index2: " + index2);

        char[] numbers = charListToCharArray(number, input);

        if (String.valueOf(numbers).equals("ans")) {
            numbers = frame.getLastResult();
        }

        System.out.println("number: " + String.valueOf(numbers));
        return Float.parseFloat(String.valueOf(numbers));
    }

    private static char[] charListToCharArray(ArrayList<Character> number, ArrayList<Character> input){
        char[] numbers = new char[number.size()];

        for(int i = 0; i < numbers.length; i++){
            numbers[i] = number.get(i);
        }

        return numbers;
    }

    private static void removeInputLine(ArrayList<Character> input){
        for(int i = 0; i <= secondNumberIndexes[1] - firstNumberIndexes[0]; i++){
            input.remove(firstNumberIndexes[0]);
        }
    }

    private static void remodellInput(ArrayList<Character> input){
        boolean next = true;
        System.out.println("remodelling");
        while(next) {
            for (int i = 0; i < input.size() - 1; i++) {
                char currentInput = input.get(i);
                char nextInput = input.get(i + 1);

                /*if(currentInput == '^'){
                    if(input.get(i - 1) != ')'){
                        int exponent = (int) getSecondNumber(i, input);
                        float number = getFirstNumber(i, input);
                        char[] numberArray = String.valueOf(number).toCharArray();

                        if (exponent > 0) {
                            ArrayList<Character> resultArray = new ArrayList<>();

                            for(int x = 0; x < exponent; x++){
                                for(int z = 0; z < numberArray.length; z++){
                                    resultArray.add(numberArray[z]);
                                }

                                if(x != exponent - 1){
                                    resultArray.add('*');
                                }
                            }

                            result = charListToCharArray(resultArray, input);
                        }
                        if (exponent == 0) {
                            result = "1".toCharArray();
                        }
                        if (exponent < 0) {
                            result = ("(1/" + number + "^" + exponent + ")").toCharArray();
                        }
                    }else{

                    }

                    inputResult(input);
                    break;
                }*/

                if(checkOperatorsOr(currentInput) && checkOperatorsOr(nextInput)){
                    if(currentInput == nextInput){
                        input.remove(i);
                        break;
                    }
                }

                if (currentInput == ' ') {
                    input.remove(i);
                    break;
                }

                if (!checkOperatorsOr(currentInput) && nextInput == '(') {
                    input.add(i +1, '*');
                    break;
                }

                if(checkOperatorsOrPoint(currentInput) && checkOperatorsOrLine(nextInput)){
                    getSecondNumber(i, input);
                    input.add(secondNumberIndexes[1] + 1, ')');
                    input.add(i + 1, '(');
                }

                if(currentInput == '(' && nextInput == '+'){
                    input.remove(i + 1);
                    break;
                }

                if (checkOperatorsOrLine(currentInput) && checkOperatorsOrLine(nextInput)) {
                    input.remove(currentInput);
                    input.add(i, '-');
                    input.remove(i + 1);
                    break;
                }

                if(i == input.size() - 2){
                    next = false;
                    break;
                }
            }
        }
    }

    /*public static void main(String[] args) {
        ArrayList<Character> input = new ArrayList<>();
        input.add('1');
        input.add('2');
        input.add('3');
        input.add(1, '4');
        input.add(2, '5');
        System.out.println(input);
    }*/
}
