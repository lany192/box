package com.github.lany192.box.sample.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ViewModelActivity;
import com.github.lany192.box.sample.databinding.ActivitySplashBinding;
import com.github.lany192.box.sample.ui.main.MainActivity;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/splash")
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends ViewModelActivity<SplashViewModel,ActivitySplashBinding> {

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_HIDE_BAR)
                .init();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.getWelcome().observe(this, s -> binding.textView.setText(s));
        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 1000);
    }
}