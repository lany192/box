package com.github.lany192.box.sample.ui.login;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.lany192.arch.activity.BindingActivity;
import com.github.lany192.box.sample.databinding.ActivityLoginBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends BindingActivity<ActivityLoginBinding> {
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = getViewModel(LoginViewModel.class);
    }
}