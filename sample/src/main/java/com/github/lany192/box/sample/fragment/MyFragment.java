package com.github.lany192.box.sample.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.fragment.FragmentConfig;
import com.github.lany192.box.sample.activity.HelloActivity;
import com.github.lany192.box.sample.databinding.FragmentMyBinding;

public class MyFragment extends BaseFragment<FragmentMyBinding> {

    @NonNull
    @Override
    public FragmentConfig getConfig() {
        return FragmentConfig.builder()
                .build();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        binding.myDebugView.setOnClickListener(v -> {
            log.i("我点击了进入调试模式");
            startActivity(new Intent(getContext(), HelloActivity.class));
        });
    }
}
