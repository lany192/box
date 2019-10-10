package com.github.lany192.box.sample.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.inputmethod.EditorInfo;

import com.flyco.tablayout.SlidingTabLayout;
import com.github.lany192.box.adapter.TabAdapter;
import com.github.lany192.box.adapter.TabItem;
import com.github.lany192.box.config.FragmentConfig;
import com.github.lany192.box.dialog.InputDialog;
import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.filter.MoneyInputFilter;
import com.hjq.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class IndexFragment extends BaseFragment {
    @BindView(R.id.index_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.index_view_pager)
    ViewPager mViewPager;

    @NonNull
    @Override
    protected FragmentConfig getConfig(FragmentConfig config) {
        return config.layoutId(R.layout.fragment_index)
                .toolBarLayoutId(R.layout.toolbar_index);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        List<TabItem> tabs = new ArrayList<>();
        tabs.add(new TabItem("列表1", new SubTabFragment()));
        tabs.add(new TabItem("列表2", new SubTabFragment()));
        tabs.add(new TabItem("列表3", new SubTabFragment()));
        mViewPager.setAdapter(new TabAdapter(getChildFragmentManager(), tabs));
        mTabLayout.setViewPager(mViewPager);
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
