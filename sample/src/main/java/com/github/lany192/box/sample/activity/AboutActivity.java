package com.github.lany192.box.sample.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.github.lany192.box.activity.ActivityConfig;
import com.github.lany192.box.activity.BaseActivity;
import com.github.lany192.box.activity.ViewBindingActivity;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.databinding.ActivityAboutBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @author Administrator
 */
public class AboutActivity extends ViewBindingActivity<ActivityAboutBinding> {
//
//    @NonNull
//    @Override
//    public ActivityConfig getConfig() {
//        return ActivityConfig.builder()
//                .layoutId(R.layout.activity_about)
//                .statusBarDarkFont(true)
//                .transparentStatusBar(true)
//                .toolBarLayoutId(R.layout.toolbar_default)
//                .keyboardEnable(true)
//                .hasBackBtn(true)
//                .build();
//    }
//
//    @Override
//    protected void init(Bundle savedInstanceState) {
//
//    }

    @NotNull
    @Override
    protected ActivityAboutBinding getViewBinding() {
        return ActivityAboutBinding.inflate(getLayoutInflater());
    }
}