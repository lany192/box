package com.github.lany192.box.sample.ui.login;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.BindingActivity;
import com.github.lany192.box.sample.databinding.ActivityLoginBinding;
import com.gyf.immersionbar.ImmersionBar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/login")
public class LoginActivity extends BindingActivity<ActivityLoginBinding> {
    private LoginViewModel viewModel;

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true)
                .titleBar(binding.toolbar)
                .init();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = getActivityViewModel(LoginViewModel.class);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}