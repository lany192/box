package com.github.lany192.box.sample.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.lany192.box.activity.BindingActivity;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.databinding.ActivitySettingsBinding;
import com.github.lany192.box.sample.ui.about.AboutActivity;
import com.github.lany192.box.sample.ui.browser.BrowserActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.ToastUtils;

public class SettingsActivity extends BindingActivity<ActivitySettingsBinding> {
    private SettingsViewModel viewModel;

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
        viewModel = getViewModel(SettingsViewModel.class);
        binding.toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        binding.versionView.setOnClickListener(v -> ToastUtils.show("已经是最新版本"));
        binding.cacheView.setOnClickListener(v -> ToastUtils.show("清除成功"));
        binding.permissionView.setOnClickListener(v -> ToastUtils.show("设置权限"));
        binding.protocolView.setOnClickListener(v -> {
            Intent intent = new Intent(this, BrowserActivity.class);
            intent.putExtra("url", "http://www.baidu.com");
            startActivity(intent);
        });
        binding.privacyView.setOnClickListener(v -> {
            Intent intent = new Intent(this, BrowserActivity.class);
            intent.putExtra("url", "http://www.baidu.com");
            startActivity(intent);
        });
        binding.aboutView.setOnClickListener(v -> startActivity(new Intent(this, AboutActivity.class)));
    }
}