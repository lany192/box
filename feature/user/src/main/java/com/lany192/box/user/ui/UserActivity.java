package com.lany192.box.user.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ContentActivity;
import com.lany192.box.user.databinding.ActivityUserBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/user/main")
public class UserActivity extends ContentActivity<ActivityUserBinding> {
    @Autowired(name = "title", desc = "标题")
    String title;
    @Autowired(name = "url", desc = "链接")
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(title);
        binding.webview.loadUrl(url);
        log.i("标题:" + title + ",链接:" + url);
    }
}