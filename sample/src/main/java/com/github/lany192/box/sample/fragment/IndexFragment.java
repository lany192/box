package com.github.lany192.box.sample.fragment;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.github.lany192.box.config.FragmentConfig;
import com.github.lany192.box.dialog.InputDialog;
import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.filter.MoneyInputFilter;
import com.github.lany192.box.sample.fragment.city.CityFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
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
        mViewPager2.setAdapter(new FragmentStateAdapter(this){

                @Override
                public int getItemCount() {
                    return 2;
                }

                @NonNull
                @Override
                public Fragment createFragment(int position) {
                    switch (position) {
                        case 0:
                            return new SubTabFragment();
                        case 1:
                            return new CityFragment();
                        default:
                            return null;
                    }
                }
        });
        mViewPager2.setOffscreenPageLimit(2);
        new TabLayoutMediator(mTabLayout, mViewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("标题1");
                case 1:
                    tab.setText("标题2");
                default:
                    tab.setText("错误");
            }
        }).attach();
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
