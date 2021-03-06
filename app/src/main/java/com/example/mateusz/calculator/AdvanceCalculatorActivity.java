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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class AdvanceCalculatorActivity extends AppCompatActivity {

    private boolean darkMode = false;
    private TextView inputField;
    private Equation equation;
    private Locale currentLocale;
    private DecimalFormatSymbols otherSymbols;
    private DecimalFormat formatValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        darkMode = intent.getBooleanExtra("darkMode", false); //if it's a string you stored.
        if(darkMode) setContentView(R.layout.advance_calculator_dark);
        else setContentView(R.layout.advance_calculator);
        inputField = findViewById(R.id.inputField);
        equation = new Equation();
        currentLocale = Locale.getDefault();
        otherSymbols = new DecimalFormatSymbols(currentLocale);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        formatValue = new DecimalFormat("#.#E0");
        formatValue.setGroupingSize(12);
        formatValue.setMaximumFractionDigits(7);
        formatValue.setMaximumIntegerDigits(11);
        formatValue.setDecimalFormatSymbols(otherSymbols);
        formatValue.setRoundingMode(RoundingMode.HALF_UP);
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
        equation.setWaitForDigit(false);
    }

    @SuppressLint("SetTextI18n")
    public void makeEquation(View view) {
        String check;
        Button equationSign = (Button) view;
        try {
            if (equationSign.getText().toString().equals("=")) {
                if (!Double.isNaN(equation.getFirstDigit())) {
                    if(!equation.isEqualSignClicked()) equation.setSecondDigit(Double.parseDouble(inputField.getText().toString()));
                    equation.makeEquation(equation);
                    check = "" + equation.getValue();
                    if(check.length() > 12) inputField.setText("" + formatValue.format(equation.getValue()));
                    else inputField.setText("" + equation.getValue());
                    equation.setFirstDigit(Double.parseDouble(inputField.getText().toString()));
                    equation.setEqualSignClicked(true);
                }
                else {
                    if(inputField.getText().length() > 0) {
                        equation.setFirstDigit(Double.parseDouble(inputField.getText().toString()));
                        equation.setEqualSignClicked(true);
                    }
                }
            } else if(equationSign.getText().toString().equals("-") && inputField.getText().toString().isEmpty()) {
                inputField.setText("-");
                equation.setWaitForDigit(true);
            } else {
                equation.setOperationSign(equationSign.getText().toString());
                if(!equation.isEqualSignClicked() && !equation.isWaitForDigit()) {
                    if (Double.isNaN(equation.getFirstDigit())) {
                        equation.setFirstDigit(Double.parseDouble(inputField.getText().toString()));
                        check = "" + equation.getValue();
                        if(check.length() > 12) inputField.setText("" + formatValue.format(equation.getFirstDigit()));
                        else inputField.setText("" + equation.getFirstDigit());
                    } else {
                        equation.setSecondDigit(Double.parseDouble(inputField.getText().toString()));
                        equation.makeEquation(equation);
                        check = "" + equation.getValue();
                        if(check.length() > 12) inputField.setText("" + formatValue.format(equation.getValue()));
                        else inputField.setText("" + equation.getValue());
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

    @SuppressLint("SetTextI18n")
    public void makeOneDigitEquation(View view) {
        String check;
        Button equationSign = (Button) view;
        try {
            equation.setOperationSign(equationSign.getText().toString());
            if(!inputField.getText().toString().equals("")) {
                equation.setFirstDigit(Double.parseDouble(inputField.getText().toString()));
                equation.makeEquation(equation);
                check = "" + equation.getValue();
                if(check.length() > 12) inputField.setText("" + formatValue.format(equation.getValue()));
                else inputField.setText("" + equation.getValue());
            }
            equation.setClearScreen(true);
            equation.setWaitForDigit(true);
        } catch (Exception ex) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, ex.getMessage(), duration);
            toast.show();
            inputField.setText("");
            equation.setFirstDigit(Double.NaN);
            equation.setSecondDigit(Double.NaN);
        }
        equation.setWaitForDigit(true);
    }

    public void clearInputField(View view) {
        inputField.setText("");
        equation.clearAll();
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
        savedInstanceState.putString("inputField", inputField.getText().toString());
        savedInstanceState.putDouble("firstDigit", equation.getFirstDigit());
        savedInstanceState.putDouble("secondDigit", equation.getSecondDigit());
        savedInstanceState.putString("operationSign", equation.getOperationSign());
        savedInstanceState.putBoolean("clearScreen", equation.isClearScreen());
        savedInstanceState.putBoolean("equalSignClicked", equation.isEqualSignClicked());
        savedInstanceState.putBoolean("waitForDigit", equation.isWaitForDigit());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        inputField.setText(savedInstanceState.getString("inputField"));
        equation.setFirstDigit(savedInstanceState.getDouble("firstDigit"));
        equation.setSecondDigit(savedInstanceState.getDouble("secondDigit"));
        equation.setOperationSign(savedInstanceState.getString("operationSign"));
        equation.setClearScreen(savedInstanceState.getBoolean("clearScreen"));
        equation.setEqualSignClicked(savedInstanceState.getBoolean("equalSignClicked"));
        equation.setWaitForDigit(savedInstanceState.getBoolean("waitForDigit"));
    }
}

