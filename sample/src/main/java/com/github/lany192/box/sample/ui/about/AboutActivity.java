package com.github.lany192.box.sample.ui.about;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.github.lany192.box.activity.ActivityConfig;
import com.github.lany192.box.activity.BaseActivity;
import com.github.lany192.box.sample.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AboutActivity extends BaseActivity {
    AboutViewModel aboutViewModel;

    @NonNull
    @Override
    public ActivityConfig getConfig() {
        return ActivityConfig.builder()
                .layoutId(R.layout.activity_about)
                .statusBarDarkFont(true)
                .transparentStatusBar(true)
                .toolBarLayoutId(R.layout.toolbar_default)
                .keyboardEnable(true)
                .hasBackBtn(true)
                .build();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        AboutViewModel vm = new ViewModelProvider(this).get(AboutViewModel.class);
    }
}