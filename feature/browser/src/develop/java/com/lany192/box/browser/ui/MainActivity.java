package com.lany192.box.browser.ui;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.BrowserRouter;
import com.lany192.box.browser.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(v -> BrowserRouter.startBrowser("百度一下", "https://www.baidu.com"));
    }
}