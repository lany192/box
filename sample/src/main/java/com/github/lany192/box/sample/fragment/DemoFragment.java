package com.github.lany192.box.sample.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.fragment.FragmentConfig;
import com.github.lany192.box.sample.R;


public class DemoFragment extends BaseFragment {
//    @BindView(R.id.image)
//    ImageView imageView;
//    @BindView(R.id.image2)
//    ImageView imageView2;
//    @BindView(R.id.image3)
//    ImageView imageView3;
//    @BindView(R.id.image4)
//    ImageView imageView4;

    @NonNull
    @Override
    public FragmentConfig getConfig() {
        return FragmentConfig.builder()
                .layoutId(R.layout.fragment_demo)
                .build();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
//        ImageUtils.show(imageView, "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1905962969,819482129&fm=26&gp=0.jpg", RoundedCornersTransform.CornerType.TOP_LEFT, 20);
//        ImageUtils.circle(imageView2, R.mipmap.a);
////        ImageUtils.showFullWidth(imageView3, "https://iknow-pic.cdn.bcebos.com/b3119313b07eca806b025475902397dda1448318");
//        ImageUtils.showOneTimeGif(imageView4, R.mipmap.test);
    }
//
//    @OnClick(R.id.button)
//    void buttonClicked() {
//        showLoadingDialog();
//        new Handler().postDelayed(() -> {
//            ImageUtils.show(imageView3, "https://iknow-pic.cdn.bcebos.com/b3119313b07eca806b025475902397dda1448318",true);
//            cancelLoadingDialog();
//        }, 5 * 1000);
//    }
//
//    @OnClick(R.id.button2)
//    void button2Clicked() {
//        HelloDialog dialog = new HelloDialog();
//        dialog.show(this);
//    }
//
//    @OnClick(R.id.button3)
//    void button3Clicked() {
//        SimpleDialog dialog = new SimpleDialog();
//        dialog.setTitle("haha");
//        dialog.setMessage("这里是呃逆荣");
//        dialog.setRightBtn("点我");
//        dialog.show(this);
//    }
}
