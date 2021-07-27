package com.github.lany192.box.sample.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.github.lany192.box.activity.ActivityConfig;
import com.github.lany192.box.activity.BaseActivity;
import com.github.lany192.box.sample.R;

public class HelloActivity extends BaseActivity {

    @NonNull
    @Override
    public ActivityConfig getConfig() {
        return ActivityConfig.builder()
                .layoutId(R.layout.activity_hello2)
                .statusBarDarkFont(true)
                .transparentStatusBar(true)
                .orientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                .build();
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}