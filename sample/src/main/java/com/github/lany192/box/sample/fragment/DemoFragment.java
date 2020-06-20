package com.github.lany192.box.sample.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.github.lany192.box.config.FragmentConfig;
import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.helper.ImageLoader;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.utils.RoundedCornersTransform;

import butterknife.BindView;
import butterknife.OnClick;

public class DemoFragment extends BaseFragment {
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.image2)
    ImageView imageView2;
    @BindView(R.id.image3)
    ImageView imageView3;
    @BindView(R.id.image4)
    ImageView imageView4;

    @NonNull
    @Override
    protected FragmentConfig getConfig(FragmentConfig config) {
        return config.layoutId(R.layout.fragment_demo);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ImageLoader.of().show(imageView, "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1905962969,819482129&fm=26&gp=0.jpg", RoundedCornersTransform.CornerType.TOP_LEFT, 20);
        ImageLoader.of().circle(imageView2, R.mipmap.a);
//        ImageLoader.of().showFullWidth(imageView3, "https://iknow-pic.cdn.bcebos.com/b3119313b07eca806b025475902397dda1448318");
        ImageLoader.of().showOneTimeGif(imageView4, R.mipmap.test);
    }

    @OnClick(R.id.button)
    void buttonClicked() {
        showLoadingDialog();
        new Handler().postDelayed(() -> {
            ImageLoader.of().showFullWidth(imageView3, "https://iknow-pic.cdn.bcebos.com/b3119313b07eca806b025475902397dda1448318");
            cancelLoadingDialog();
        }, 5 * 1000);
    }
}
