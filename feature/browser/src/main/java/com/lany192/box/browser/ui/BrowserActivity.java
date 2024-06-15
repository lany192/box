package com.lany192.box.browser.ui;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ViewBindingActivity;
import com.github.lany192.arch.utils.BarUtils;
import com.lany192.box.browser.databinding.ActivityBrowserBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/browser/main")
public class BrowserActivity extends ViewBindingActivity<ActivityBrowserBinding> {
    @Autowired(name = "title", desc = "标题")
    String title;
    @Autowired(name = "url", desc = "链接")
    String url;

    @Override
    public void initImmersionBar() {
        BarUtils.init(this).keyboardEnable(true).titleBar(binding.toolbar).init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(title);
        binding.webview.loadUrl(url);
        log.i("标题:" + title + ",链接:" + url);
    }

    /**
     * javascript回调
     *
     * @param callback 回调方法
     * @param value    回调值
     */
    private void callback(String callback, String value) {
        if (!TextUtils.isEmpty(callback) && !TextUtils.isEmpty(callback.trim())) {
            binding.webview.loadUrl("javascript:" + callback + "('" + value + "')");
        }
    }
}