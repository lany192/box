package com.github.lany192.box.sample.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.github.lany192.box.activity.ActivityConfig;
import com.github.lany192.box.activity.BaseActivity;
import com.github.lany192.box.sample.R;

public class AboutActivity extends BaseActivity {

    @NonNull
    @Override
    public ActivityConfig getConfig() {
        return ActivityConfig.builder()
                .layoutId(R.layout.activity_about)
                .statusBarDarkFont(true)
                .transparentStatusBar(true)
                .hasToolbar(true)
                .keyboardEnable(true)
                .hasBackBtn(true)
                .build();
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}