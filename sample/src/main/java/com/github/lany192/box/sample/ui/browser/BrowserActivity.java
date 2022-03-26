package com.github.lany192.box.sample.ui.browser;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ToolbarActivity;
import com.github.lany192.box.sample.databinding.ActivityBrowserBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/browser")
public class BrowserActivity extends ToolbarActivity<ActivityBrowserBinding> {
    @Autowired(name = "title", desc = "标题")
    String title;
    @Autowired(name = "url", desc = "链接")
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(title);
        binding.webview.loadUrl(url);
        log.i("test");
    }
}