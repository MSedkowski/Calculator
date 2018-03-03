package com.example.mateusz.calculator;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView inputField = findViewById(R.id.inputField);
    private double valueOne = Double.NaN;
    private double valueTwo;
    private String operation = null;
    private boolean clearFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if(inputField.getText().length() != 0) {
            String temp = inputField.getText().toString();
            inputField.setText(temp + value.toString());
        }
        else {
            inputField.setText(value.toString());
        }
    }

    @SuppressLint("SetTextI18n")
    public void makeEquation(View view) {
            Button equationSign = (Button) view;
            operation = equationSign.getText().toString();
            if(!Double.isNaN(valueOne)) {
                valueTwo = Double.parseDouble(inputField.getText().toString());
                if(operation.equals("/") && valueTwo == 0) {
                    inputField.setText(R.string.error_divide_by_0);
                    valueOne = Double.NaN;
                }
                else {
                    switch(operation) {
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
                        case "±":
                            valueOne *= -1;
                            break;
                        default:
                            break;

                    }
                    inputField.setText("" + valueOne);
                    if(operation.equals("=")) valueOne = Double.NaN;
                    operation = null;
                }
                clearFlag = true;
            }
            else {
                try {
                    valueOne = Double.parseDouble(inputField.getText().toString());
                    clearFlag = true;
                    inputField.setText("" + valueOne);
                } catch (Exception e) {
                    inputField.setText("");
                }
            }
    }


    public void clearInputField(View view) {
        inputField.setText("");
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
                inputField.setText(buttonText);
        }
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
