package com.example.my_calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentInput = "";
    private String prevInput = "";
    private String operator = "";

    private String result = "";
    private boolean isOperatorClicked = false;
    private boolean isResultDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.textView);

        setupButtons();
    }

    private void setupButtons() {
        Button btnOne = findViewById(R.id.btn_one);
        Button btnTwo = findViewById(R.id.btn_two);
        Button btnThree = findViewById(R.id.btn_three);
        Button btnFour = findViewById(R.id.btn_four);
        Button btnFive = findViewById(R.id.btn_five);
        Button btnSix = findViewById(R.id.btn_six);
        Button btnSeven = findViewById(R.id.btn_seven);
        Button btnEight = findViewById(R.id.btn_eight);
        Button btnNine = findViewById(R.id.btn_nine);
        Button btnZero = findViewById(R.id.btn_zero);

        Button btnPlus = findViewById(R.id.btn_plus);
        Button btnMinus = findViewById(R.id.btn_minus);
        Button btnMultiply = findViewById(R.id.btn_multiplier);
        Button btnDivide = findViewById(R.id.btn_slash);
        Button btnEqual = findViewById(R.id.btn_equal);

        Button btnClear = findViewById(R.id.btn_c);
        Button btnComma = findViewById(R.id.btn_comma);
        Button btnSquare = findViewById(R.id.btn_square);
        Button btnBackspace = findViewById(R.id.btn_left);

        View.OnClickListener numberClickListener = v -> {
            Button button = (Button) v;
            if (isResultDisplayed && isOperatorClicked) {
                prevInput = result;
                currentInput = "";
                isResultDisplayed = false;
            }

            if (isResultDisplayed && !isOperatorClicked) {
                prevInput = "";
                currentInput = "";
                isResultDisplayed = false;
            }

            if (isOperatorClicked && !isResultDisplayed) {
                currentInput = "";
                isOperatorClicked = false;
            }
            currentInput += button.getText().toString();
            display.setText(prevInput + " " + operator + " " + currentInput);
        };

        btnOne.setOnClickListener(numberClickListener);
        btnTwo.setOnClickListener(numberClickListener);
        btnThree.setOnClickListener(numberClickListener);
        btnFour.setOnClickListener(numberClickListener);
        btnFive.setOnClickListener(numberClickListener);
        btnSix.setOnClickListener(numberClickListener);
        btnSeven.setOnClickListener(numberClickListener);
        btnEight.setOnClickListener(numberClickListener);
        btnNine.setOnClickListener(numberClickListener);
        btnZero.setOnClickListener(numberClickListener);

        View.OnClickListener operatorClickListener = v -> {
            Button button = (Button) v;

            if (isResultDisplayed) {
                prevInput = result;
                currentInput = "";
                operator = button.getText().toString();
                isResultDisplayed = false;
                isOperatorClicked = true;
                display.setText(prevInput + " " + operator);
                return;
            }

            if (!currentInput.isEmpty()) {
                if (!prevInput.isEmpty() && !operator.isEmpty()) {
                    calculate();
                } else {
                    prevInput = currentInput;
                }
                operator = button.getText().toString();
                isOperatorClicked = true;
                display.setText(prevInput + " " + operator);
            }
        };

        btnPlus.setOnClickListener(operatorClickListener);
        btnMinus.setOnClickListener(operatorClickListener);
        btnMultiply.setOnClickListener(operatorClickListener);
        btnDivide.setOnClickListener(operatorClickListener);

        // "="
        btnEqual.setOnClickListener(v -> {
            if (!currentInput.isEmpty() && !prevInput.isEmpty() && !operator.isEmpty()) {
                calculate();
            }
        });

        // "C"
        btnClear.setOnClickListener(v -> {
            currentInput = "";
            prevInput = "";
            operator = "";
            result = "";
            display.setText("0");
        });

        // ","
        btnComma.setOnClickListener(v -> {
            if (!currentInput.contains(".")) {
                currentInput += ".";
                display.setText(prevInput + " " + operator + " " + currentInput);
            }
        });

        btnSquare.setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                double num = Double.parseDouble(currentInput);
                double result = Math.sqrt(num);
                currentInput = String.valueOf(result);
                display.setText(prevInput + " " + operator + " " + currentInput);
            }
        });

        btnBackspace.setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                display.setText(prevInput + " " + operator + " " + currentInput);
            }
        });
    }

    private void calculate() {
        double result = 0;
        double num1 = Double.parseDouble(prevInput);
        double num2 = Double.parseDouble(currentInput);

        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    display.setText("Error");
                    return;
                }
                break;
        }

        this.result = String.valueOf(result);
        display.setText(this.result);

        prevInput = this.result;
        currentInput = "";
        operator = "";
        isResultDisplayed = true;
    }
}
