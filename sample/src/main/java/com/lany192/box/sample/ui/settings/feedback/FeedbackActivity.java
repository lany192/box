package com.lany192.box.sample.ui.settings.feedback;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ContentActivity;
import com.lany192.box.sample.databinding.ActivityFeedbackBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/feedback")
public class FeedbackActivity extends ContentActivity<ActivityFeedbackBinding> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.button.setOnClickListener(v -> {

        });
    }
}