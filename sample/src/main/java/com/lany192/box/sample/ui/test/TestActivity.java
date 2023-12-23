package com.lany192.box.sample.ui.test;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ContentActivity;
import com.lany192.box.sample.MinIoHelper;
import com.lany192.box.sample.databinding.ActivityTestBinding;

import java.io.File;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/test")
public class TestActivity extends ContentActivity<ActivityTestBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.button.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "需要选择文件"), 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            File file = new File(uri.toString());
            binding.result.setText(file.getPath());
            MinIoHelper.getInstance().upload("hello", "test.png", file.getPath(), new MinIoHelper.MinIoCallback() {
                @Override
                public void progress(int progress) {
                    binding.result.setText("进度："+progress);
                }
            });
        }
    }
}