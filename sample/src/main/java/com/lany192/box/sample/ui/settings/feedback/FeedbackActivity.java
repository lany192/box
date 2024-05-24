package com.lany192.box.sample.ui.settings.feedback;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ViewBindingActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.lany192.box.sample.databinding.ActivityFeedbackBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/feedback")
public class FeedbackActivity extends ViewBindingActivity<ActivityFeedbackBinding> {

    @NonNull
    @Override
    public ImmersionBar initImmersionBar() {
        return super.initImmersionBar().titleBar(binding.toolbar);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.button.setOnClickListener(v -> {

        });
    }
}