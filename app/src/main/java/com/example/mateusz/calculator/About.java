package com.example.mateusz.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        boolean darkMode = intent.getBooleanExtra("darkMode", false); //if it's a string you stored.
        if(darkMode) setContentView(R.layout.activity_about_dark);
        else setContentView(R.layout.activity_about);
    }

    public void backButtonPressed(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
