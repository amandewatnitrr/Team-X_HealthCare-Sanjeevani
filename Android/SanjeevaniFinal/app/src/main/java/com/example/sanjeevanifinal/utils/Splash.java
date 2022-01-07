package com.example.sanjeevanifinal.utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sanjeevanifinal.activities.LoginActivity;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(() -> {
            Intent homeIntent = new Intent(Splash.this, LoginActivity.class);
            startActivity(homeIntent);
            finish();

        },SPLASH_TIME_OUT);
    }
}
