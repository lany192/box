package com.github.lany192.box.sample.activity;

import android.os.Bundle;

import com.github.lany192.box.activity.ViewBindingActivity;
import com.github.lany192.box.sample.databinding.ActivityAboutBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Administrator
 */
public class AboutActivity extends ViewBindingActivity<ActivityAboutBinding> {

    @NotNull
    @Override
    public ActivityAboutBinding getViewBinding() {
        return ActivityAboutBinding.inflate(getLayoutInflater());
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

    }
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

}