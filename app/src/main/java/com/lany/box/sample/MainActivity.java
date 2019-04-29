package com.lany.box.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import com.hjq.toast.ToastUtils;
import com.lany.box.activity.DaggerActivity;
import com.lany.box.adapter.ViewPagerAdapter;
import com.lany.box.config.UIConfig;
import com.lany.box.dialog.InputDialog;
import com.lany.box.sample.filter.MoneyInputFilter;
import com.lany.box.sample.fragment.HelloFragment;
import com.lany.box.sample.fragment.IndexFragment;
import com.lany.box.sample.fragment.MyFragment;
import com.lany.box.widget.NavigationBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends DaggerActivity implements MainContract.View {
    @BindView(R.id.main_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.main_navigation_bar)
    NavigationBar mNavigationBar;
    @Inject
    MainPresenter mPresenter;

    private long exitTime = 0; // 第一次按退出的时间

    @Override
    protected UIConfig getConfig() {
        UIConfig config = super.getConfig();
        config.setHasBackBtn(false);
        config.setKeyboardEnable(false);
        config.setToolBarLayoutId(R.layout.toolbar_main);
        return config;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        List<Fragment> items = new ArrayList<>();
        items.add(new IndexFragment());
        items.add(new HelloFragment());
        items.add(new MyFragment());
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), items));
        mNavigationBar.setupWithViewPager(mViewPager);
    }

    @OnClick(R.id.custom_toolbar_edit_btn)
    void btnClicked() {
        mPresenter.sayClick();
    }

    @Override
    public void sayHello(String hello) {
        InputDialog dialog = new InputDialog();
        dialog.setTitle("金额");
        dialog.setHint("请输入金额");
        dialog.setInputType(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
        dialog.addInputFilter(new MoneyInputFilter());
        dialog.setMaxLength(5);
        dialog.setButtonText("提交");
        dialog.setOnInputListener(new InputDialog.OnInputListener() {
            @Override
            public void onResult(CharSequence result) {
                ToastUtils.show(result);
            }
        });
        dialog.show(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 3000) {
                ToastUtils.show("再按一次退出应用");
                exitTime = System.currentTimeMillis();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
