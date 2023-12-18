package com.lany192.box.sample.ui.settings.daynight;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ContentActivity;
import com.github.lany192.utils.KVUtils;
import com.lany192.box.sample.Constants;
import com.lany192.box.sample.R;
import com.lany192.box.sample.databinding.ActivityDayNightBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/daynight")
public class DayNightActivity extends ContentActivity<ActivityDayNightBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetView();
        binding.auto.setOnClickListener(v -> resetThemeType(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM));
        binding.dark.setOnClickListener(v -> resetThemeType(AppCompatDelegate.MODE_NIGHT_YES));
        binding.light.setOnClickListener(v -> resetThemeType(AppCompatDelegate.MODE_NIGHT_NO));
    }

    private void resetThemeType(int type) {
        if (getThemeType() == type) {
            //log.i("已选择，忽略重启");
            return;
        }
        KVUtils.putInt(Constants.KEY_THEME_TYPE, type);
        resetView();
        AppCompatDelegate.setDefaultNightMode(type);
    }

    private int getThemeType() {
        return KVUtils.getInt(Constants.KEY_THEME_TYPE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    private void resetView() {
        int themeType = getThemeType();
        binding.auto.setIcon(themeType == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM ? R.drawable.set_sign_tick : 0);
        binding.dark.setIcon(themeType == AppCompatDelegate.MODE_NIGHT_YES ? R.drawable.set_sign_tick : 0);
        binding.light.setIcon(themeType == AppCompatDelegate.MODE_NIGHT_NO ? R.drawable.set_sign_tick : 0);
    }
}