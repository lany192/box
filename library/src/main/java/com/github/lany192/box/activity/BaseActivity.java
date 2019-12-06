package com.github.lany192.box.activity;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.R;
import com.github.lany192.box.config.ActivityConfig;
import com.github.lany192.box.dialog.LoadingDialog;
import com.github.lany192.box.event.NetWorkEvent;
import com.github.lany192.box.interfaces.OnDoubleClickListener;
import com.github.lany192.box.mvp.view.BaseView;
import com.github.lany192.box.utils.ClickUtil;
import com.github.lany192.box.utils.DensityUtils;
import com.github.lany192.box.utils.ViewUtils;
import com.github.lany192.view.StateLayout;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 通用基类
 */
public abstract class BaseActivity extends AppCompatActivity implements StateLayout.OnRetryListener, BaseView {
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);
    protected FragmentActivity self;
    private View mToolbar;
    private StateLayout mStateLayout;
    private Unbinder mUnBinder;
    private LoadingDialog mLoadingDialog;
    private ActivityConfig config;
    private CompositeDisposable disposables = new CompositeDisposable();

    /**
     * 获取Activity的界面配置
     *
     * @return ActivityConfig
     */
    @NonNull
    protected abstract ActivityConfig getConfig(ActivityConfig config);

    protected abstract void init(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.self = this;
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        config = getConfig(new ActivityConfig()
                .layoutId(R.layout.ui_default)
                .fullscreen(false)
                .hasToolbar(true)
                .toolBarLayoutId(R.layout.toolbar_default)//hasToolbar值为true时，该值无效
                .hasBackBtn(true)//hasToolbar值为true时，该值无效
                .keyboardEnable(true)
                .statusBarColor(android.R.color.white)//如果transparentStatusBar为true，该值无效
                .statusBarDarkFont(true)
                .toolbarHeight(DensityUtils.dp2px(48))
                .transparentStatusBar(true)
                .title(getTitle())//hasToolbar为true,以及能找到title id时该设置生效
        );
        initStatusBar();
        onBeforeSetContentView();
        setContentView(getContentView());
        mUnBinder = ButterKnife.bind(this);
        init(savedInstanceState);
    }

    private View getContentView() {
        RelativeLayout rootView = new RelativeLayout(this);
        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mStateLayout = new StateLayout(this);
        mStateLayout.setOnRetryListener(this);
        View contentView = LayoutInflater.from(this).inflate(config.getLayoutId(), null);
        if (config.getContentColor() > 0) {
            contentView.setBackgroundResource(config.getContentColor());
        }
        mStateLayout.addView(contentView);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (config.isHasToolbar()) {
            mToolbar = LayoutInflater.from(this).inflate(config.getToolBarLayoutId(), null);
            mToolbar.setId(R.id.toolbar);
            mToolbar.setOnTouchListener(new OnDoubleClickListener(view -> onToolbarDoubleClick()));
            mToolbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    config.getToolbarHeight()));
            if (config.getToolbarColor() > 0) {
                mToolbar.setBackgroundResource(config.getToolbarColor());
            }
            if (config.isTransparentStatusBar()) {
                ViewUtils.setPaddingSmart(mToolbar);
            }
            rootView.addView(mToolbar);
            setBarTitle(config.getTitle());
            View backBtn = mToolbar.findViewById(R.id.toolbar_back_btn);
            if (config.isHasBackBtn()) {
                if (backBtn == null) {
                    throw new IllegalArgumentException("Please use the 'R.id.toolbar_back_btn' field to back in custom toolbar layout.");
                }
                backBtn.setOnClickListener(v -> {
                    if (!ClickUtil.isFast()) {
                        backAction();
                    }
                });
            } else {
                if (backBtn != null) {
                    backBtn.setVisibility(View.GONE);
                }
            }
            lp.addRule(RelativeLayout.BELOW, mToolbar.getId());
        }
        rootView.addView(mStateLayout, lp);
        return rootView;
    }

    /**
     * 用法：https://github.com/gyf-dev/ImmersionBar
     */
    private void initStatusBar() {
        ImmersionBar bar = ImmersionBar.with(this);
        if (config.isFullscreen()) {
            bar.hideBar(BarHide.FLAG_HIDE_BAR);//隐藏状态栏或导航栏或两者，不写默认不隐藏
        } else {
            bar.navigationBarColorInt(Color.WHITE);
            bar.navigationBarDarkIcon(true);
            if (ImmersionBar.isSupportStatusBarDarkFont()) {
                bar.statusBarDarkFont(config.isStatusBarDarkFont());
            }
            if (config.isTransparentStatusBar()) {
                bar.statusBarAlpha(0.0f).statusBarColor(android.R.color.transparent);
            } else {
                bar.statusBarColor(config.getStatusBarColor()).fitsSystemWindows(true);
            }
            bar.keyboardEnable(config.isKeyboardEnable());
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
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            hideSoftInput();
        }
        return super.onTouchEvent(event);
    }

    protected void hideSoftInput() {
        //点击空白区域收起输入法
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void setBarTitle(@StringRes int resId) {
        setBarTitle(getString(resId));
    }

    public void setBarTitle(CharSequence title) {
        if (config.isHasToolbar()) {
            TextView titleText = mToolbar.findViewById(R.id.toolbar_title_text);
            if (titleText == null) {
                throw new IllegalArgumentException("Please use the 'R.id.toolbar_title_text' field to set title in custom toolbar layout.");
            }
            if (config.isHasToolbar() && !TextUtils.isEmpty(title)) {
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
     * 接收处理Disposable对象
     *
     * @param disposable
     */
    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    @Override
    protected void onDestroy() {
        //ImmersionBar.with(this).destroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (null != mUnBinder) {
            mUnBinder.unbind();
        }
        if (disposables != null && disposables.isDisposed()) {
            disposables.dispose();
            disposables = null;
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetWorkEvent event) {
        log.i("onEvent: 网络发生了变化");
    }

    @Override
    public void showEmpty() {
        mStateLayout.showEmpty();
    }

    @Override
    public void showEmpty(String msg) {
        mStateLayout.showEmpty(msg);
    }

    @Override
    public void showContent() {
        mStateLayout.showContent();
    }

    @Override
    public void showNoWifi() {
        mStateLayout.showNetwork();
    }

    @Override
    public void showError() {
        mStateLayout.showError();
    }

    @Override
    public void showError(String msg) {
        mStateLayout.showError(msg);
    }

    @Override
    public void showLoading() {
        mStateLayout.showLoading();
    }

    @Override
    public void showLoadingDialog() {
        showLoadingDialog(getString(R.string.loading));
    }

    @Override
    public void showLoadingDialog(CharSequence message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog();
        }
        mLoadingDialog.setMessage(message);
        if (!mLoadingDialog.isAdded()) {
            mLoadingDialog.show(getSupportFragmentManager(), TAG);
        }
    }

    @Override
    public void cancelLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isAdded()) {
            mLoadingDialog.cancel();
        }
    }
}
