package com.lany.box.sample.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.lany.box.config.FragmentConfig;
import com.lany.box.fragment.BaseFragment;
import com.lany.box.sample.R;

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
