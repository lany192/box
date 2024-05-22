package com.lany192.box.hello.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.lany192.box.hello.databinding.ActivityHelloBinding;
import com.lany192.box.router.provider.BrowserProvider;
import com.lany192.box.router.provider.LoginProvider;
import com.lany192.box.router.provider.MathProvider;
import com.lany192.box.router.provider.UserProvider;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/hello/main")
public class HelloActivity extends AppCompatActivity {
    @Autowired
    BrowserProvider browserProvider;
    @Autowired
    MathProvider mathProvider;
    @Autowired
    UserProvider userProvider;
    @Autowired
    LoginProvider loginProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHelloBinding binding = ActivityHelloBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.test1.setOnClickListener(v -> browserProvider.startBrowser("测试", "https://www.baidu.com"));
        binding.test2.setOnClickListener(v -> mathProvider.startMath());
        binding.test3.setOnClickListener(v -> userProvider.startUser("dddd", "eeee"));
        binding.test4.setOnClickListener(v -> loginProvider.startLogin());
        if (userProvider != null) {
            binding.textView.setText("用户id:" + userProvider.getUserId());
        }
    }
}