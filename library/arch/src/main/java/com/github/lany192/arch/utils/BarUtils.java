package com.github.lany192.arch.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.github.lany192.arch.R;
import com.gyf.immersionbar.ImmersionBar;

public class BarUtils {

    public static ImmersionBar init(Fragment fragment) {
        return ImmersionBar.with(fragment)
                .statusBarDarkFont(fragment.getResources().getBoolean(R.bool.status_bar_dark_font))
                .navigationBarColor(R.color.white_bg)
                .navigationBarDarkIcon(fragment.getResources().getBoolean(R.bool.navigation_bar_dark_icon));
    }

    public static ImmersionBar init(FragmentActivity activity) {
        return ImmersionBar.with(activity)
                .statusBarDarkFont(activity.getResources().getBoolean(R.bool.status_bar_dark_font))
                .navigationBarColor(R.color.white_bg)
                .navigationBarDarkIcon(activity.getResources().getBoolean(R.bool.navigation_bar_dark_icon));
    }
}
