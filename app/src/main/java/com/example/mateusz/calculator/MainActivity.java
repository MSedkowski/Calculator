package com.example.mateusz.calculator;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private boolean darkMode;
    private ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_view);
        constraintLayout = findViewById(R.id.mainLayout);
        final Switch toggle = findViewById(R.id.darkModeSwitch);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    darkMode = true;
                    constraintLayout.setBackgroundColor(Color.BLACK);
                    toggle.setTextColor(Color.WHITE);
                } else {
                    darkMode = false;
                    constraintLayout.setBackgroundColor(Color.WHITE);
                    toggle.setTextColor(Color.BLACK);
                }
            }
        });
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
