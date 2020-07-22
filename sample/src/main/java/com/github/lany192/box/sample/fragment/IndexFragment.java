package com.github.lany192.box.sample.fragment;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.github.lany192.box.dialog.InputDialog;
import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.fragment.FragmentConfig;
import com.github.lany192.box.fragment.TabPager;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.filter.MoneyInputFilter;
import com.github.lany192.box.sample.fragment.city.CityFragment;
import com.google.android.material.tabs.TabLayout;
import com.hjq.toast.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class IndexFragment extends BaseFragment {
    @BindView(R.id.index_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.index_view_pager)
    ViewPager2 mViewPager2;

    @NonNull
    @Override
    protected FragmentConfig getConfig(FragmentConfig config) {
        return config.layoutId(R.layout.fragment_index)
                .toolBarLayoutId(R.layout.toolbar_index);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        new TabPager(this, mViewPager2, mTabLayout)
                .addTab("精选", new DemoFragment())
                .addTab("标题1", new SubTabFragment())
                .addTab("标题2", new CityFragment())
                .addTab("标题3", new SubTabFragment())
                .addTab("标题4", new CityFragment())
                .addTab("标题5", new SubTabFragment());
    }

    @OnClick(R.id.custom_toolbar_edit_btn)
    void btnClicked() {
        InputDialog dialog = new InputDialog();
        dialog.setTitle("金额");
        dialog.setHint("请输入金额");
        dialog.setInputType(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
        dialog.addInputFilter(new MoneyInputFilter());
        dialog.setMaxLength(5);
        dialog.setButtonText("提交");
        dialog.setOnInputListener(ToastUtils::show);
        dialog.show(self);
    }
}
