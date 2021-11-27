package com.github.lany192.box.sample.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.github.lany192.box.binding.BindingActivity;
import com.github.lany192.box.sample.databinding.ActivityLoginBinding;
import com.github.lany192.box.sample.ui.main.MainActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BindingActivity<ActivityLoginBinding> {
    private SplashViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}