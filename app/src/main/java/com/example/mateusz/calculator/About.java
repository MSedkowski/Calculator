package com.example.mateusz.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void backButtonPressed(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
