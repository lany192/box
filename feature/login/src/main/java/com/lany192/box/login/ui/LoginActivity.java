package com.lany192.box.login.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.VMVBActivity;
import com.hjq.toast.Toaster;
import com.lany192.box.login.databinding.ActivityLoginBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/login/main")
public class LoginActivity extends VMVBActivity<LoginViewModel, ActivityLoginBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.login.setOnClickListener(v -> Toaster.show("登录成功"));
    }
}