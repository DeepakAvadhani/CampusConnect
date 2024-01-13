package com.example.campusconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.example.campusconnect.User.Authentication.LoginActivity;

public class SplashScreen extends AppCompatActivity {
    private static int splash_timeout = 3000;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        animationView = findViewById(R.id.animationview);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, splash_timeout);
    }
}