package com.example.mateusz.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView inputField;
    private Integer storedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputField = (TextView) findViewById(R.id.inputField);
    }

    public void addDigit(View view) {
        Button digit = (Button) view;
        String buttonText = digit.getText().toString();
        Integer value = Integer.parseInt(buttonText);
        if(inputField.getText().length() != 0) {
            String temp = inputField.getText().toString();
            inputField.setText(temp + value.toString());
        }
        else
            inputField.setText(value.toString());
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
    public void addition(View view) {
        if(storedValue == null) {
            storedValue = Integer.parseInt(inputField.getText().toString());
            clearInputField(view);
        }
        else {
            Log.d("Stored value: ", storedValue.toString());
            storedValue += Integer.parseInt(inputField.getText().toString());
            inputField.setText(storedValue.toString());
        }
    }

    public void equationEquals(View view) {
        inputField.setText(storedValue.toString());
        storedValue = null;
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
