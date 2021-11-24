package com.github.lany192.box.sample.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.ui.about.AboutViewModel;
import com.github.lany192.box.sample.ui.main.MainActivity;
import com.hjq.toast.ToastUtils;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
//    @Inject
//    SplashViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SplashViewModel vm = new ViewModelProvider(this).get(SplashViewModel.class);

        ToastUtils.show(vm.toString());

//        startActivity(new Intent(this, MainActivity.class));
//        finish();
    }
}