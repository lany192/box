package com.lany192.box.sample.ui.encrypt;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ViewBindingActivity;
import com.github.lany192.blackbox.BlackBox;
import com.lany192.box.sample.databinding.ActivityEncryptBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/encrypt")
public class EncryptActivity extends ViewBindingActivity<ActivityEncryptBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.encrypt.setOnClickListener(v -> {
            String text = binding.input.getText().toString();
            binding.encryptResult.setText(BlackBox.method01(text));
        });
        binding.decrypt.setOnClickListener(v -> {
            String text = binding.encryptResult.getText().toString();
            binding.decryptResult.setText(BlackBox.method02(text));
        });
    }
}