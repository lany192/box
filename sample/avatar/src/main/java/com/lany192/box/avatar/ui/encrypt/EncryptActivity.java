package com.lany192.box.avatar.ui.encrypt;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ViewBindingActivity;
import com.github.lany192.arch.utils.BarUtils;
import com.github.lany192.blackbox.BlackBox;
import com.lany192.box.avatar.databinding.ActivityEncryptBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/encrypt")
public class EncryptActivity extends ViewBindingActivity<ActivityEncryptBinding> {
//    @Override
//    public void initImmersionBar() {
//        BarUtils.init(this).keyboardEnable(true).titleBar(binding.toolbar).init();
//    }

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