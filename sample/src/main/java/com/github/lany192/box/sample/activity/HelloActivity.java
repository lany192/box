package com.github.lany192.box.sample.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.lany192.box.binding.BindingActivity;
import com.github.lany192.box.sample.databinding.ActivityHello2Binding;

public class HelloActivity extends BindingActivity<ActivityHello2Binding> {
    private boolean show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.button.setOnClickListener(v -> {
            if (show) {
                show = false;
                controller.setAppearanceLightStatusBars(true);
                controller.setAppearanceLightNavigationBars(false);
            } else {
                show = true;
                controller.setAppearanceLightStatusBars(false);
                controller.setAppearanceLightNavigationBars(true);
            }
        });
    }
}