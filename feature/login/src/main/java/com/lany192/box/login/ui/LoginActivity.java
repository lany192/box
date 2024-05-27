package com.lany192.box.login.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ViewModelActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.lany192.box.login.databinding.ActivityLoginBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/login/main")
public class LoginActivity extends ViewModelActivity<LoginViewModel, ActivityLoginBinding> {
    @NonNull
    @Override
    public ImmersionBar initImmersionBar() {
        return super.initImmersionBar().titleBar(binding.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.login.setOnClickListener(v -> Toaster.show("登录成功"));
    }
}