package com.github.lany192.box.sample.activity;

import android.os.Bundle;

import com.github.lany192.box.activity.ViewBindingActivity;
import com.github.lany192.box.sample.databinding.ActivityAboutBinding;
import com.github.lany192.box.view.ActivityBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Administrator
 */
public class AboutActivity extends ViewBindingActivity<ActivityAboutBinding> {

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
//        getBinding().getContent().ge
    }

    @NotNull
    @Override
    public ActivityAboutBinding getContentView() {
        return ActivityAboutBinding.inflate(getLayoutInflater());
    }
}