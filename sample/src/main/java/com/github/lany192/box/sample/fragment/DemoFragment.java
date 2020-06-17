package com.github.lany192.box.sample.fragment;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.github.lany192.box.adapter.MultiAdapter;
import com.github.lany192.box.config.FragmentConfig;
import com.github.lany192.box.delegate.Delegate;
import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.helper.ImageLoader;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Menu;
import com.github.lany192.box.sample.delegate.HelloDelegate;
import com.github.lany192.box.sample.delegate.MenuDelegate;
import com.github.lany192.box.widget.ShowView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DemoFragment extends BaseFragment {
    @BindView(R.id.image)
    ImageView imageView;

    @NonNull
    @Override
    protected FragmentConfig getConfig(FragmentConfig config) {
        return config.layoutId(R.layout.fragment_demo);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ImageLoader.of().circle(imageView, "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1905962969,819482129&fm=26&gp=0.jpg",20);
    }
}
