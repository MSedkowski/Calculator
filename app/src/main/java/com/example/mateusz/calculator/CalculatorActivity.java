package com.example.mateusz.calculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class CalculatorActivity extends AppCompatActivity {
    private boolean darkMode = false;
    private TextView inputField;
    private double valueOne = Double.NaN;
    private double valueTwo;
    private String operation = null;
    private boolean clearFlag = false;
    private boolean equationCalculated = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_calculator);
        Intent intent = getIntent();
        darkMode = intent.getBooleanExtra("darkMode", false); //if it's a string you stored.
        inputField = findViewById(R.id.inputField);
    }

    @SuppressLint("SetTextI18n")
    public void addDigit(View view) {
        if(clearFlag) {
            inputField.setText("");
            clearFlag = false;
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
            equationCalculated = false;
        }
    }

    @SuppressLint("SetTextI18n")
    public void makeEquation(View view) {
        try {
            DecimalFormat formater;
            Button equationSign = (Button) view;
            if (equationSign.getText().toString().equals("=")) {
                if (!equationCalculated) {
                    valueTwo = Double.parseDouble(inputField.getText().toString());
                }
                equationCalculated = false;
            } else if (equationSign.getText().toString().equals("±")) {
                if(Double.isNaN(valueOne)) {
                    operation = equationSign.getText().toString();
                    valueOne = Double.parseDouble(inputField.getText().toString());
                    valueOne *= -1;
                    valueTwo = valueOne;
                    formater = checkTheValue(valueOne);
                    inputField.setText(formater.format(valueOne));
                }
                else {
                    Double check = Double.parseDouble(inputField.getText().toString());
                    if(valueOne == valueTwo  && valueOne == check){
                        valueOne *= -1;
                        formater = checkTheValue(valueOne);
                        inputField.setText(formater.format(valueOne));
                    }
                    else {
                        valueTwo = Double.parseDouble(inputField.getText().toString());
                        valueTwo *= -1;
                        formater = checkTheValue(valueTwo);
                        inputField.setText(formater.format(valueTwo));
                    }
                }
                clearFlag = true;
                equationCalculated = true;
            } else {
                operation = equationSign.getText().toString();
                valueTwo = Double.parseDouble(inputField.getText().toString());
            }
            if (!equationCalculated) {
                if (!Double.isNaN(valueOne)) {
                    if (operation.equals("/") && valueTwo == 0) {
                        inputField.setText(R.string.error_divide_by_0);
                        valueOne = Double.NaN;
                        valueTwo = Double.NaN;
                    } else {
                        switch (operation) {
                            case "+":
                                valueOne += valueTwo;
                                break;
                            case "-":
                                valueOne -= valueTwo;
                                break;
                            case "*":
                                valueOne *= valueTwo;
                                break;
                            case "/":
                                valueOne /= valueTwo;
                                break;
                            default:
                                break;

                        }
                        formater = checkTheValue(valueOne);
                        Log.wtf("Formater: ", "" + formater);
                        inputField.setText(formater.format(valueOne));
                        equationCalculated = true;
                    }
                    clearFlag = true;
                } else {
                    valueOne = Double.parseDouble(inputField.getText().toString());
                    clearFlag = true;
                    formater = checkTheValue(valueOne);
                    inputField.setText(formater.format(valueOne));
                }
            }
        } catch (Exception e) {
            inputField.setText("");
        }
    }


    public void clearInputField(View view) {
        inputField.setText("");
        valueOne = Double.NaN;
        valueTwo = Double.NaN;
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

    private DecimalFormat checkTheValue(Double value) {
        String[] numberOfDigits = value.toString().split(".");
        int numberOfDigitsBeforeZero = numberOfDigits[0].length();
        int numberOfDigitsAfterZero = numberOfDigits[1].length();
        if(numberOfDigitsAfterZero + numberOfDigitsBeforeZero > 12) {
            numberOfDigitsAfterZero = 12 - numberOfDigitsBeforeZero;
        }
        String formater = "";
        for(int i = 0; i < numberOfDigitsBeforeZero; i++) {
            formater += "#";
        }
        formater += ".";
        for(int i = 0; i < numberOfDigitsAfterZero; i++) {
            formater += "#";
        }
        Log.wtf("Formater: ", formater);
        return new DecimalFormat(formater);
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
        savedInstanceState.putBoolean("clearFlag", clearFlag);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.

        inputField.setText(savedInstanceState.getString("inputField"));
        clearFlag = savedInstanceState.getBoolean("clearFlag");
    }
}
