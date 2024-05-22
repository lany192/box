package com.lany192.box.user.ui;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.lany192.box.user.R;
import com.lany192.box.user.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.title.setText(getString(R.string.app_name) + "独立运行模式");
        binding.button.setOnClickListener(v -> UserRouter.start("百度一下", "https://www.baidu.com"));
    }
}