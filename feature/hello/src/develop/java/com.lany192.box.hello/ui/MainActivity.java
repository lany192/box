package com.lany192.box.hello.ui;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.lany192.box.hello.R;
import com.lany192.box.hello.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.title.setText(getString(R.string.app_name) + "独立运行模式" + System.currentTimeMillis());
        binding.button.setOnClickListener(v -> HelloRouter.start());
    }
}