package com.github.lany192.box.sample.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.github.lany192.box.binding.BindingActivity;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.databinding.ActivityAboutBinding;
import com.github.lany192.box.sample.databinding.ActivityLoginBinding;
import com.github.lany192.box.sample.ui.about.AboutViewModel;


import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity  extends BindingActivity<ActivityLoginBinding> {
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }
}