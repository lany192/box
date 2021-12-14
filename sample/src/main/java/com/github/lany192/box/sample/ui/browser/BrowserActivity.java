package com.github.lany192.box.sample.ui.browser;

import android.content.Intent;
import android.os.Bundle;

import com.github.lany192.arch.activity.BindingActivity;
import com.github.lany192.box.sample.databinding.ActivityBrowserBinding;
import com.gyf.immersionbar.ImmersionBar;

public class BrowserActivity extends BindingActivity<ActivityBrowserBinding> {

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true)
                .titleBar(binding.toolbar)
                .init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        binding.webview.loadUrl(url);
    }
}