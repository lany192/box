package com.lany192.box.sample.ui.settings.feedback;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ContentActivity;
import com.github.lany192.utils.DensityUtils;
import com.lany192.box.sample.BuildConfig;
import com.lany192.box.sample.databinding.ActivityFeedbackBinding;
import com.zhihu.matisse.GlideEngine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/feedback")
public class FeedbackActivity extends ContentActivity<ActivityFeedbackBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.button.setOnClickListener(view -> Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.WEBP), false)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, BuildConfig.APPLICATION_ID + ".fileprovider", "images"))
                .maxSelectable(1)
                .gridExpectedSize(DensityUtils.dp2px(120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .showSingleMediaType(true)
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .forResult(new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                    }
                }));
    }

}