package com.github.lany192.box.sample.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.ui.about.AboutViewModel;


import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AboutViewModel vm = new ViewModelProvider(this).get(AboutViewModel.class);
    }
}