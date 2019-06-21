package com.github.lany192.box.sample.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.github.lany192.box.config.FragmentConfig;
import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.sample.R;

import butterknife.BindView;

public class MyFragment extends BaseFragment {
    @BindView(R.id.image)
    ImageView imageView;

    @NonNull
    @Override
    protected FragmentConfig getConfig(FragmentConfig config) {
        return config.layoutId(R.layout.fragment_my);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}
