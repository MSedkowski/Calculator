package com.example.mateusz.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private boolean darkMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_view);
    }

    public void startSimpleCalculator(View view) {
        Intent simpleCalculator = new Intent(MainActivity.this, CalculatorActivity.class);
        simpleCalculator.putExtra("darkMode", darkMode); //Optional parameters
        MainActivity.this.startActivity(simpleCalculator);
    }

    public void startAdvanceCalculator(View view) {
        Intent advanceCalculator = new Intent(MainActivity.this, AdvanceCalculatorActivity.class);
        advanceCalculator.putExtra("darkMode", darkMode); //Optional parameters
        MainActivity.this.startActivity(advanceCalculator);
    }

    public void startAboutApp(View view) {
        Intent about = new Intent(MainActivity.this, About.class);
        about.putExtra("darkMode", darkMode); //Optional parameters
        MainActivity.this.startActivity(about);
    }

}
