package com.lany192.box.sample.ui.hello;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.items.PageActivity;
import com.lany192.box.sample.data.binder.ArticleBinder;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/hello")
public class HelloActivity extends PageActivity<HelloViewModel> {
    {
        register(new ArticleBinder());
    }
}