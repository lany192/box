package com.lany192.box.hello.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.toast.Toaster;
import com.lany192.box.hello.R;
import com.lany192.box.hello.databinding.ActivityHelloBinding;
import com.lany192.box.router.provider.BrowserProvider;
import com.lany192.box.router.provider.LoginProvider;
import com.lany192.box.router.provider.MathProvider;
import com.lany192.box.router.provider.UserProvider;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.List;

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

    private final int REQUEST_CODE_CHOOSE = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHelloBinding binding = ActivityHelloBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.test1.setOnClickListener(v -> browserProvider.startBrowser("测试", "https://www.baidu.com"));
        binding.test2.setOnClickListener(v -> mathProvider.startMath());
        binding.test3.setOnClickListener(v -> userProvider.startUserInfo());
        binding.test4.setOnClickListener(v -> loginProvider.startLogin());
        if (userProvider != null) {
            binding.textView.setText("用户id:" + userProvider.getUserId());
        }
        binding.test5.setOnClickListener(v -> imagePicker());
        binding.test6.setOnClickListener(v -> Toaster.show(userProvider.getToken()));
    }

    private void imagePicker() {
        Matisse.from(HelloActivity.this)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(9)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .showPreview(false) // Default is `true`
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> mSelected = Matisse.obtainResult(data);
            Toaster.show(mSelected.toString());
            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }
}