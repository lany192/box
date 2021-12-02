package com.github.lany192.box.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.lany192.box.R;
import com.github.lany192.box.event.NetWorkEvent;
import com.github.lany192.box.interfaces.OnDoubleClickListener;
import com.github.lany192.box.mvp.BaseView;
import com.github.lany192.box.utils.ViewUtils;
import com.github.lany192.utils.ClickUtils;
import com.github.lany192.utils.DensityUtils;
import com.github.lany192.view.StateLayout;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 通用基类
 */
public abstract class BaseActivity extends BasicActivity
        implements StateLayout.OnRetryListener, BaseView {
    private View toolBarView;
    private StateLayout stateLayout;
    private CompositeDisposable compositeDisposable;

    @NonNull
    public abstract ActivityConfig getConfig();

    protected abstract void init(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initStatusBar();
        onBeforeSetContentView();
        setContentView(getContentView());
        init(savedInstanceState);
    }

    private View getContentView() {
        RelativeLayout rootView = new RelativeLayout(this);
        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        stateLayout = new StateLayout(this);
        stateLayout.setOnRetryListener(this);
        stateLayout.addView(LayoutInflater.from(this).inflate(getConfig().getLayoutId(), null));
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (getConfig().hasToolbar()) {
            toolBarView = LayoutInflater.from(this).inflate(getConfig().getToolBarLayoutId() == 0 ? R.layout.toolbar_default : getConfig().getToolBarLayoutId(), null);
            toolBarView.setId(R.id.toolbar);
            toolBarView.setOnTouchListener(new OnDoubleClickListener(view -> onToolbarDoubleClick()));
            toolBarView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getConfig().getToolbarHeight() == 0 ? DensityUtils.dp2px(48) : getConfig().getToolbarHeight()));
            if (getConfig().isTransparentStatusBar()) {
                ViewUtils.setPaddingSmart(toolBarView);
            }
            rootView.addView(toolBarView);
            setTitle(TextUtils.isEmpty(getConfig().getTitle()) ? getTitle() : getConfig().getTitle());
            View backBtn = toolBarView.findViewById(R.id.toolbar_back_btn);
            if (getConfig().isHasBackBtn()) {
                if (backBtn == null) {
                    throw new IllegalArgumentException("Please use the 'R.id.toolbar_back_btn' field to back in custom toolbar layout.");
                }
                backBtn.setOnClickListener(v -> {
                    if (!ClickUtils.isFast()) {
                        backAction();
                    }
                });
            } else {
                if (backBtn != null) {
                    backBtn.setVisibility(View.GONE);
                }
            }
            lp.addRule(RelativeLayout.BELOW, toolBarView.getId());
        }
        rootView.addView(stateLayout, lp);
        return rootView;
    }

    /**
     * 用法：https://github.com/gyf-dev/ImmersionBar
     */
    private void initStatusBar() {
        ImmersionBar bar = ImmersionBar.with(this);
        if (getConfig().isFullscreen()) {
            bar.hideBar(BarHide.FLAG_HIDE_BAR);//隐藏状态栏或导航栏或两者，不写默认不隐藏
        } else {
            bar.navigationBarColorInt(Color.WHITE);
            bar.navigationBarDarkIcon(true);
            if (ImmersionBar.isSupportStatusBarDarkFont()) {
                bar.statusBarDarkFont(getConfig().isStatusBarDarkFont());
            }
            if (getConfig().isTransparentStatusBar()) {
                bar.statusBarAlpha(0.0f).statusBarColor(android.R.color.transparent);
            } else {
                bar.statusBarColor(getConfig().getStatusBarColor() == 0 ? android.R.color.white : getConfig().getStatusBarColor()).fitsSystemWindows(true);
            }
            bar.keyboardEnable(getConfig().isKeyboardEnable());
            //特殊机型处理,状态栏背景改成黑色
            if (!TextUtils.isEmpty(Build.MODEL) && Build.MODEL.contains("A33")) {
                bar.statusBarColor(android.R.color.black);
            }
        }
        bar.init();
    }


    protected void onBeforeSetContentView() {

    }

    protected void onToolbarDoubleClick() {
        //要实现复写该方法
    }

    @Override
    public void setTitle(@StringRes int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getConfig().hasToolbar()) {
            TextView titleText = toolBarView.findViewById(R.id.toolbar_title_text);
            if (titleText == null) {
                throw new IllegalArgumentException("Please use the 'R.id.toolbar_title_text' field to set title in custom toolbar layout.");
            }
            if (!TextUtils.isEmpty(title)) {
                titleText.setText(title);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backAction();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void backAction() {
        hideSoftInput();
        onBackPressed();
    }

    @Override
    public void onRetry() {
        log.i("点击重试");
    }


    /**
     * 处理disposable
     */
    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetWorkEvent event) {
        //log.i("onEvent: 网络发生了变化");
    }

    @Override
    public void showEmpty() {
        stateLayout.showEmpty();
    }

    @Override
    public void showEmpty(String msg) {
        stateLayout.showEmpty(msg);
    }

    @Override
    public void showContent() {
        stateLayout.showContent();
    }

    @Override
    public void showNoWifi() {
        stateLayout.showNetwork();
    }

    @Override
    public void showError() {
        stateLayout.showError();
    }

    @Override
    public void showError(String msg) {
        stateLayout.showError(msg);
    }

    @Override
    public void showLoading() {
        stateLayout.showLoading();
    }

    protected <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        return new ViewModelProvider(this).get(modelClass);
    }
}
