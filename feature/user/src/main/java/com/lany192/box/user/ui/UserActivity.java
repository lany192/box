package com.lany192.box.user.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ViewBindingActivity;
import com.github.lany192.utils.KVUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.lany192.box.user.databinding.ActivityUserBinding;

import java.util.Random;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/user/main")
public class UserActivity extends ViewBindingActivity<ActivityUserBinding> {

    @NonNull
    @Override
    public ImmersionBar initImmersionBar() {
        return super.initImmersionBar().titleBar(binding.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        binding.test1.setText("标题:" + title + ",链接:" + url);
//        binding.button.setOnClickListener(v -> {
//            int userId = Math.abs(new Random().nextInt());
//            KVUtils.putString("key_user_id", String.valueOf(userId));
//            Toaster.show("保存成功:" + userId);
//        });
    }
}