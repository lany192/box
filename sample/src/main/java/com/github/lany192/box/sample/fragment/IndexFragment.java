package com.github.lany192.box.sample.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.fragment.FragmentConfig;
import com.github.lany192.box.sample.R;


public class IndexFragment extends BaseFragment {
//    @BindView(R.id.index_tab_layout)
//    TabLayout mTabLayout;
//    @BindView(R.id.index_view_pager)
//    ViewPager2 mViewPager2;

    @NonNull
    @Override
    public FragmentConfig getConfig() {
        return FragmentConfig.builder()
                .layoutId(R.layout.fragment_index)
                .toolBarLayoutId(R.layout.toolbar_index)
                .build();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
//        new TabPager(this, mViewPager2, mTabLayout)
//                .addTab("精选", new DemoFragment())
//                .addTab("妹子", new GirlFragment())
//                .addTab("标题1", new SubTabFragment())
//                .addTab("城市", new CityFragment());
    }
//
//    @OnClick(R.id.custom_toolbar_edit_btn)
//    void btnClicked() {
//        InputDialog dialog = new InputDialog();
//        dialog.setTitle("金额");
//        dialog.setHint("请输入金额");
//        dialog.setInputType(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
//        dialog.addInputFilter(new MoneyInputFilter());
//        dialog.setMaxLength(5);
//        dialog.setButtonText("提交");
//        dialog.setOnInputListener(ToastUtils::show);
//        dialog.show(this);
//    }
}
