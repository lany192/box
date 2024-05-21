package com.lany192.box.hello.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.lany192.box.hello.R;
import com.lany192.box.hello.databinding.ActivityHelloBinding;
import com.lany192.box.router.provider.BrowserProvider;
import com.lany192.box.router.provider.MathProvider;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/hello/main")
public class HelloActivity extends AppCompatActivity {
    @Autowired
    BrowserProvider browserProvider;
    @Autowired
    MathProvider mathProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHelloBinding binding = ActivityHelloBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.test1.setOnClickListener(v -> browserProvider.startBrowser("测试", "https://www.baidu.com"));
        binding.test2.setOnClickListener(v -> mathProvider.startMath());
    }
}