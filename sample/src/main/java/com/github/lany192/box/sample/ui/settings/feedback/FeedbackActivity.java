package com.github.lany192.box.sample.ui.settings.feedback;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.BindingActivity;
import com.github.lany192.arch.databinding.ToolbarDefaultBinding;
import com.github.lany192.box.sample.BuildConfig;
import com.github.lany192.box.sample.databinding.ActivityFeedbackBinding;
import com.github.lany192.utils.DensityUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/feedback")
public class FeedbackActivity extends BindingActivity<ActivityFeedbackBinding, ToolbarDefaultBinding> {

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
                .forResult(1));
    }

    @NonNull
    @Override
    public ToolbarDefaultBinding getToolbarBinding() {
        return ToolbarDefaultBinding.inflate(getLayoutInflater());
    }
}