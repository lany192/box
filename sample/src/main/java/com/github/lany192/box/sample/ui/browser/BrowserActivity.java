package com.github.lany192.box.sample.ui.browser;

import android.content.Intent;
import android.os.Bundle;

import com.github.lany192.box.activity.BindingActivity;
import com.github.lany192.box.sample.databinding.ActivityBrowserBinding;

public class BrowserActivity extends BindingActivity<ActivityBrowserBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        binding.webview.loadUrl(url);
    }
}