package com.fit.benefit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Timer;
import java.util.TimerTask;

public class IntroductoryActivity extends AppCompatActivity {

    Timer timer;

    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        lottieAnimationView = findViewById(R.id.loading);
        lottieAnimationView.animate().translationY(1400).setDuration(1800).setStartDelay(4700);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroductoryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 6300);
    }
}