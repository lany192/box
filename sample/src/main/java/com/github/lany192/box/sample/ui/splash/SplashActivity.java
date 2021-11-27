package com.github.lany192.box.sample.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.ui.main.MainActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    SplashViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}