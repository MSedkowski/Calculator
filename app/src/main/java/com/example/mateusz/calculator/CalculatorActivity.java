package com.example.mateusz.calculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class CalculatorActivity extends AppCompatActivity {
    private TextView inputField;
    private Equation equation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        boolean darkMode = intent.getBooleanExtra("darkMode", false); //if it's a string you stored.
        if(darkMode) setContentView(R.layout.simple_calculator_dark);
        else setContentView(R.layout.simple_calculator);
        inputField = findViewById(R.id.inputField);
        equation = new Equation();
    }

    @SuppressLint("SetTextI18n")
    public void addDigit(View view) {
        if(equation.isClearScreen()) {
            inputField.setText("");
            equation.setClearScreen(false);
        }
        Button digit = (Button) view;
        String buttonText = digit.getText().toString();
        Integer value = Integer.parseInt(buttonText);
        if(inputField.getText().length() < 12) {
            if(inputField.getText().length() != 0) {
                String temp = inputField.getText().toString();
                inputField.setText(temp + value.toString());
            }
            else {
                inputField.setText(value.toString());
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void makeEquation(View view) {
        DecimalFormat formater;
        Button equationSign = (Button) view;
        try {
            if (equationSign.getText().toString().equals("=")) {
                if (!Double.isNaN(equation.getFirstDigit())) {
                    if(!equation.isEqualSignClicked()) equation.setSecondDigit(Double.parseDouble(inputField.getText().toString()));
                    equation.makeEquation(equation);
                    formater = checkTheValue(equation.getValue());
                    inputField.setText("" + formater.format(equation.getValue()));
                    equation.setFirstDigit(Double.parseDouble(inputField.getText().toString()));
                    equation.setEqualSignClicked(true);
                }
                else {
                    if(inputField.getText().length() > 0) {
                        equation.setFirstDigit(Double.parseDouble(inputField.getText().toString()));
                        equation.setEqualSignClicked(true);
                    }
                }
            } else {
                equation.setOperationSign(equationSign.getText().toString());
                if(!equation.isEqualSignClicked() && (!equation.isWaitForDigit() || equation.getOperationSign().equals("±"))) {
                    if (Double.isNaN(equation.getFirstDigit())) {
                        equation.setFirstDigit(Double.parseDouble(inputField.getText().toString()));
                        formater = checkTheValue(equation.getFirstDigit());
                        inputField.setText("" + formater.format(equation.getFirstDigit()));
                    } else {
                        equation.setSecondDigit(Double.parseDouble(inputField.getText().toString()));
                        equation.makeEquation(equation);
                        formater = checkTheValue(equation.getValue());
                        inputField.setText("" + formater.format(equation.getValue()));
                    }
                    equation.setClearScreen(true);
                }
                equation.setEqualSignClicked(false);
                equation.setWaitForDigit(true);
            }
        } catch (Exception ex) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, ex.getMessage(), duration);
            toast.show();
            inputField.setText("");
        }
    }

    private DecimalFormat checkTheValue(Double value) {
        String valueToString = value.toString();
        List<String> numberOfDigits = Arrays.asList(valueToString.split("\\."));
        int numberOfDigitsBeforeZero = numberOfDigits.get(0).length();
        int numberOfDigitsAfterZero = 0;
        if(numberOfDigits.size() > 1) {
            numberOfDigitsAfterZero = numberOfDigits.get(1).length();
            if(numberOfDigitsAfterZero + numberOfDigitsBeforeZero > 12) {
                numberOfDigitsAfterZero = 12 - numberOfDigitsBeforeZero;
            }
        }
        StringBuilder formater = new StringBuilder();
        for(int i = 0; i < numberOfDigitsBeforeZero; i++) {
            formater.append("#");
        }
        formater.append(".");
        for(int i = 0; i < numberOfDigitsAfterZero; i++) {
            formater.append("#");
        }
        return new DecimalFormat(formater.toString());
    }

    public void clearInputField(View view) {
        inputField.setText("");
        equation.setFirstDigit(Double.NaN);
        equation.setSecondDigit(Double.NaN);
    }

    public void bkspInput(View view) {
        if(!inputField.getText().toString().equals("")) {
            if(inputField.getText().length() != 1) {
                String temp = inputField.getText().toString().substring(0, inputField.getText().toString().length() - 1);
                inputField.setText(temp);
            }
            else {
                inputField.setText("");
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void addComa(View view) {
        String test = inputField.getText().toString();
        if(!test.contains(".")) {
            Button coma = (Button) view;
            String buttonText = coma.getText().toString();
            if(test.length() != 0) {
                inputField.setText(test + buttonText);
            }
            else
                inputField.setText("0" + buttonText);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        savedInstanceState.putString("inputField", inputField.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.

        inputField.setText(savedInstanceState.getString("inputField"));
    }
}
