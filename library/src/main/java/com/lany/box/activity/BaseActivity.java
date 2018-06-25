package com.lany.box.activity;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
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

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.gyf.barlibrary.ImmersionBar;
import com.lany.box.R;
import com.lany.box.dialog.LoadingDialog;
import com.lany.box.event.NetWorkEvent;
import com.lany.box.interfaces.OnDoubleClickListener;
import com.lany.box.mvp.view.BaseView;
import com.lany.box.utils.ClickUtil;
import com.lany.box.utils.PhoneUtils;
import com.lany.state.StateLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * 通用基类
 */
public abstract class BaseActivity extends DaggerAppCompatActivity implements StateLayout.OnRetryListener, BaseView {
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);
    protected AppCompatActivity self;
    private View mToolbar;
    private RelativeLayout mAllView;
    private TextView mTitleText;
    private StateLayout mStateLayout;
    private Unbinder mUnBinder;
    private LoadingDialog mLoadingDialog;
    protected ImmersionBar mImmersionBar;

    /**
     * 是否需要Toolbar
     */
    protected boolean hasToolbar() {
        return true;
    }

    /**
     * Toolbar是否需要显示返回键
     */
    protected boolean hasBackBtn() {
        return true;
    }

    /**
     * 状态栏的文字和图标是否改成黑色
     */
    protected boolean hasStateBarDarkMode() {
        return true;
    }

    /**
     * 返回Toolbar布局文件id
     */
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_default_layout;
    }

    protected int getToolBarHeight() {
        return getResources().getDimensionPixelSize(R.dimen.actionbar_height);
    }

    /**
     * 返回内容布局文件id
     */
    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.self = this;
        EventBus.getDefault().register(this);
        mImmersionBar = ImmersionBar
                .with(this)
                .statusBarDarkFont(hasStateBarDarkMode(), 0.2f)
                .navigationBarEnable(false);
        mImmersionBar.init();
        onBeforeSetContentView();
        mAllView = new RelativeLayout(this);
        mAllView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        if (getLayoutId() != 0) {
            View contentView = LayoutInflater.from(this).inflate(getLayoutId(), null);
            mStateLayout = new StateLayout(this);
            mStateLayout.setOnRetryListener(this);
            mStateLayout.addView(contentView);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            if (hasToolbar()) {
                initToolbar();
                layoutParams.addRule(RelativeLayout.BELOW, mToolbar.getId());
            }
            mAllView.addView(mStateLayout, layoutParams);
        } else {
            throw new IllegalArgumentException("getLayoutId() return 0 , you need a layout file resources");
        }
        setContentView(mAllView);
        mUnBinder = ButterKnife.bind(this);
        init(savedInstanceState);
    }

    /**
     * 增加View的paddingTop,增加的值为状态栏高度 (智能判断，并设置高度)
     */
    public void setPaddingSmart(Context context, View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp != null && lp.height > 0) {
                lp.height += PhoneUtils.getStatusBarHeight(context);//增高
            }
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + PhoneUtils.getStatusBarHeight(context), view.getPaddingRight(), view.getPaddingBottom());
        }
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

    private void initToolbar() {
        mToolbar = LayoutInflater.from(this).inflate(getToolBarLayoutId(), null);
        mToolbar.setOnTouchListener(new OnDoubleClickListener(view -> onToolbarDoubleClick()));
        mToolbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getToolBarHeight()));
        setPaddingSmart(this, mToolbar);
        mAllView.addView(mToolbar);

        mTitleText = mToolbar.findViewById(R.id.custom_toolbar_title_text);
        if (mTitleText == null) {
            throw new IllegalArgumentException("Please use the 'R.id.custom_toolbar_title_text' field to set title in custom toolbar layout.");
        }
        setBarTitle(getTitle());

        View backBtn = mToolbar.findViewById(R.id.custom_toolbar_back_btn);
        if (backBtn == null) {
            throw new IllegalArgumentException("Please use the 'R.id.custom_toolbar_back_btn' field to back in custom toolbar layout.");
        }
        if (hasBackBtn()) {
            backBtn.setVisibility(View.VISIBLE);
            backBtn.setOnClickListener(v -> {
                if (!ClickUtil.isFast()) {
                    backAction();
                }
            });
        } else {
            backBtn.setVisibility(View.GONE);
        }
    }

    public void setBarTitle(@StringRes int resId) {
        setBarTitle(getString(resId));
    }

    public void setBarTitle(CharSequence title) {
        if (hasToolbar() && !TextUtils.isEmpty(title)) {
            mTitleText.setText(title);
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

    @Override
    protected void onDestroy() {
        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        }
        EventBus.getDefault().unregister(this);
        if (null != mUnBinder) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    @Subscribe
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
