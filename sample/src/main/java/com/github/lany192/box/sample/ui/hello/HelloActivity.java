package com.github.lany192.box.sample.ui.hello;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ModelActivity;
import com.github.lany192.arch.databinding.ToolbarTestBinding;
import com.github.lany192.box.sample.databinding.TestBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/hello")
public class HelloActivity extends ModelActivity<HelloViewModel, TestBinding, ToolbarTestBinding> {

}