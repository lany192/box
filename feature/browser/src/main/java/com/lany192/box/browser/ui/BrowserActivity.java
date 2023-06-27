package com.lany192.box.browser.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ContentActivity;
import com.lany192.box.browser.databinding.ActivityBrowserBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/browser/main")
public class BrowserActivity extends ContentActivity<ActivityBrowserBinding> {
    @Autowired(name = "title", desc = "标题")
    String title;
    @Autowired(name = "url", desc = "链接")
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(title);
        binding.webview.loadUrl(url);
        log.i("标题:" + title);
        log.i("链接:" + url);
    }
}