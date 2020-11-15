package com.github.lany192.box.sample.fragment;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.fragment.FragmentConfig;
import com.github.lany192.box.sample.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFragment extends BaseFragment {
    @BindView(R.id.image)
    ImageView imageView;

    @NonNull
    @Override
    public FragmentConfig getConfig() {
        return new FragmentConfig().layoutId(R.layout.fragment_my);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick(R.id.my_debug_view)
    void debugClicked() {
        log.i("我点击了进入调试模式");
    }
}
