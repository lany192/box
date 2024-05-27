package com.lany192.box.user.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ViewBindingActivity;
import com.github.lany192.arch.utils.BarUtils;
import com.github.lany192.utils.ImageUtils;
import com.lany192.box.user.databinding.ActivityUserBinding;
import com.lany192.box.user.dialog.SexDialog;
import com.lany192.box.user.ui.nickname.NicknameRouter;
import com.lany192.box.user.ui.signature.SignatureRouter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/user/main")
public class UserInfoActivity extends ViewBindingActivity<ActivityUserBinding> {

    @Override
    public void initImmersionBar() {
        BarUtils.init(this).keyboardEnable(true).titleBar(binding.toolbar).init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.nickname.setOnClickListener(v -> NicknameRouter.start());
        binding.signature.setOnClickListener(v -> SignatureRouter.start());
        binding.sexView.setOnClickListener(v -> new SexDialog(false).show());
        ImageUtils.show(binding.avatar, "http://pic.imeitou.com/uploads/allimg/221021/8-221021094504.jpg");

//        binding.test1.setText("标题:" + title + ",链接:" + url);
//        binding.button.setOnClickListener(v -> {
//            int userId = Math.abs(new Random().nextInt());
//            KVUtils.putString("key_user_id", String.valueOf(userId));
//            Toaster.show("保存成功:" + userId);
//        });
    }
}