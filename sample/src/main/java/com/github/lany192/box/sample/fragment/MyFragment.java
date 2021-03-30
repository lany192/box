package com.github.lany192.box.sample.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.fragment.FragmentConfig;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.activity.AboutActivity;
import com.github.lany192.box.sample.activity.HelloActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFragment extends BaseFragment {
    @BindView(R.id.image)
    ImageView imageView;

    @NonNull
    @Override
    public FragmentConfig getConfig() {
        return FragmentConfig.builder()
                .layoutId(R.layout.fragment_my)
                .build();
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick(R.id.my_sell_view)
    void sellClicked() {
        startActivity(new Intent(getContext(), AboutActivity.class));
    }

    @OnClick(R.id.my_debug_view)
    void debugClicked() {
        log.i("我点击了进入调试模式");
        startActivity(new Intent(getContext(), HelloActivity.class));
    }
}
