package com.example.calculator;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.displayInputText);
    }

    //method to display text
    // Method to display text
    private void updateText(String newText) {
        String oldText = inputText.getText().toString();

        // If the current text is "0", replace it with newText instead of appending
        if (oldText.equals("0")) {
            inputText.setText(newText);
        } else {
            inputText.setText(oldText + newText);
        }
    }



    //Method for updating text with 0, using on click listener in activity.xml
    public void zeroButton(View view)
    {
        updateText(getResources().getString(R.string.zeroText));
    }

    //Method for updating text with 1, using on click listener in activity.xml
    public void oneButton(View view)
    {
        updateText(getResources().getString(R.string.oneText));
    }

    //Method for updating text with 2, using on click listener in activity.xml
    public void twoButton(View view)
    {
        updateText(getResources().getString(R.string.twoText));
    }

    //Method for updating text with 3, using on click listener in activity.xml
    public void threeButton(View view)
    {
        updateText(getResources().getString(R.string.threeText));
    }

    //Method for updating text with 4, using on click listener in activity.xml
    public void fourButton(View view)
    {
        updateText(getResources().getString(R.string.fourText));
    }

    //Method for updating text with 5, using on click listener in activity.xml
    public void fiveButton(View view)
    {
        updateText(getResources().getString(R.string.fiveText));
    }

    //Method for updating text with 6, using on click listener in activity.xml
    public void sixButton(View view)
    {
        updateText(getResources().getString(R.string.sixText));
    }

    //Method for updating text with 7, using on click listener in activity.xml
    public void sevenButton(View view)
    {
        updateText(getResources().getString(R.string.sevenText));
    }

    //Method for updating text with 8, using on click listener in activity.xml
    public void eightButton(View view)
    {
        updateText(getResources().getString(R.string.eightText));
    }

    //Method for updating text with 9, using on click listener in activity.xml
    public void nineButton(View view)
    {
        updateText(getResources().getString(R.string.nineText));
    }

    //Method for inserting a division symbol, using on click listener in activity.xml
    public void divideButton(View view)
    {
        updateText(getResources().getString(R.string.divideText));
    }

    //Method for inserting a multiplication symbol, using on click listener in activity.xml
    public void multiplyButton(View view)
    {
        updateText(getResources().getString(R.string.multiplyText));
    }

    //Method for inserting an addition, using on click listener in activity.xml
    public void addButton(View view)
    {
        updateText(getResources().getString(R.string.addText));
    }

    //Method for inserting a subtraction, using on click listener in activity.xml
    public void subtractButton(View view)
    {
        updateText(getResources().getString(R.string.subtractText));
    }

    //Method for inserting a decimal, using on click listener in activity.xml
    public void decimalButton(View view)
    {
        updateText(getResources().getString(R.string.decimalText));
    }

    //Method for taking the natural log of a number, using on click listener in activity.xml
    public void naturalLogButton(View view)
    {
        updateText(getResources().getString(R.string.naturalLogText));
    }

    //Method for taking the square root of a number, using on click listener in activity.xml
    public void squareRootButton(View view)
    {
        updateText(getResources().getString(R.string.squareRootText));
    }

    //Method for squaring a number, using on click listener in activity.xml
    public void squaredButton(View view)
    {
        updateText("^2");
    }

    //Method for making a power of a number, using on click listener in activity.xml
    public void xPowerYButton(View view)
    {
        updateText("x^y");
    }

    //Method for updating the sign of a number, using on click listener in activity.xml
    public void signButton(View view)
    {
        String temp;
        temp = inputText.getText().toString();
        double temp1 = 0;
        double temp2 = 0;

        temp1 = Double.parseDouble(temp);
        temp2 = temp1 * -1;
        inputText.setText(Double.toString(temp2));
    }

    //Method for updating number in box with % value, using on click listener in activity.xml
    public void percentageButton(View view)
    {
        String temp;
        temp = inputText.getText().toString();
        double temp1 = 0;
        double temp2 = 0;

        temp1 = Double.parseDouble(temp);
        temp2 = temp1 * 0.01;
        inputText.setText(Double.toString(temp2));
    }

    //Method for clearing text, using on click listener in activity.xml
    public void clearButton(View view)
    {
        inputText.setText("0");
    }


    public void equalsButton(View view) {
        String equation = inputText.getText().toString().trim();

        if (equation.isEmpty()) {
            inputText.setText("ERROR");
            return;
        }

        ArrayList<String> tokens = new ArrayList<>();
        Matcher matcher = Pattern.compile("([√]|ln|\\^2|x\\^y|[-+*/])|([0-9.]+)").matcher(equation);

        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        try {
            double result = 0;
            boolean isUnaryOperation = false;
            String operator = "";

            for (int i = 0; i < tokens.size(); i++) {
                String token = tokens.get(i);

                if (isUnaryOperation) {
                    double operand = Double.parseDouble(token);
                    result = calculate(operand, 0, operator);
                    isUnaryOperation = false;
                } else if (token.matches("[0-9.]+")) {
                    double number = Double.parseDouble(token);
                    if (operator.isEmpty()) {
                        result = number;
                    } else {
                        result = calculate(result, number, operator);
                        operator = ""; // Reset operator after use
                    }
                } else if (token.matches("[-+*/x\\^y]")) {
                    operator = token;
                } else if (token.equals("√") || token.equals("ln") || token.equals("^2")) {
                    operator = token;
                    isUnaryOperation = true;
                }
            }

            inputText.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            inputText.setText("ERROR: " + e.getMessage());
        }
    }


    double calculate(double firstNum, double secondNum, String operator) {
        switch (operator) {
            case "/":
                if (secondNum == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return firstNum / secondNum;
            case "*":
                return firstNum * secondNum;
            case "+":
                return firstNum + secondNum;
            case "-":
                return firstNum - secondNum;
            case "^2":
                return Math.pow(firstNum, 2);
            case "x^y":
                return Math.pow(firstNum, secondNum);
            case "ln":
                if (firstNum <= 0) {
                    throw new ArithmeticException("Logarithm of non-positive number");
                }
                return Math.log(firstNum);
            case "√":
                if (firstNum < 0) {
                    throw new ArithmeticException("Square root of negative number");
                }
                return Math.sqrt(firstNum);
            default:
                throw new UnsupportedOperationException("Unknown operator: " + operator);
        }
    }

}