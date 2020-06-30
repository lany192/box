package com.github.lany192.box.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.R;
import com.github.lany192.box.config.FragmentConfig;
import com.github.lany192.box.dialog.LoadingDialog;
import com.github.lany192.box.event.NetWorkEvent;
import com.github.lany192.box.interfaces.OnDoubleClickListener;
import com.github.lany192.box.mvp.view.BaseView;
import com.github.lany192.box.utils.DensityUtils;
import com.github.lany192.box.utils.ViewUtils;
import com.github.lany192.view.StateLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseFragment extends Fragment implements StateLayout.OnRetryListener, BaseView {
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);
    protected FragmentActivity self;
    private StateLayout mStateLayout;
    private Unbinder mUnBinder;
    private LoadingDialog mLoadingDialog = null;
    /**
     * 是否执行过懒加载
     */
    private boolean isLazyLoaded;
    private RelativeLayout mRootView;
    /**
     * 配置信息
     */
    private FragmentConfig config;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    /**
     * 本界面用户是否可见
     */
    private boolean userVisible;

    /**
     * 获取Fragment的界面配置
     */
    @NonNull
    protected abstract FragmentConfig getConfig(FragmentConfig config);

    /**
     * 初始化
     */
    protected abstract void init(Bundle savedInstanceState);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.self = getActivity();
        config = getConfig(new FragmentConfig()
                .layoutId(R.layout.ui_default)
                .toolbarHeight(DensityUtils.dp2px(48)));
    }

    @Override
    public void onResume() {
        super.onResume();
        userVisible = true;
        //需要在FragmentStatePagerAdapter构造方法中配置
        if (!isLazyLoaded) {
            isLazyLoaded = true;
            onLazyLoad();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        userVisible = false;
    }

    /**
     * 用户是否可见
     */
    public boolean isUserVisible() {
        return userVisible;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = new RelativeLayout(self);
        mRootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        mStateLayout = new StateLayout(self);
        mStateLayout.setOnRetryListener(this);
        View contentView = inflater.inflate(config.getLayoutId(), null);
        if (config.getContentColor() > 0) {
            contentView.setBackgroundResource(config.getContentColor());
        }
        mStateLayout.addView(contentView);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (config.isHasToolbar()) {
            View toolbar = inflater.inflate(config.getToolBarLayoutId(), null);
            toolbar.setId(R.id.toolbar);
            toolbar.setOnTouchListener(new OnDoubleClickListener(view -> onToolbarDoubleClick()));
            toolbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, config.getToolbarHeight()));
            ViewUtils.setPaddingSmart(toolbar);
            layoutParams.addRule(RelativeLayout.BELOW, toolbar.getId());
            mRootView.addView(toolbar);
        }

        mRootView.addView(mStateLayout, layoutParams);
        mUnBinder = ButterKnife.bind(this, mRootView);
        init(savedInstanceState);
        return mRootView;
    }

    public <T extends View> T findView(@IdRes int viewId) {
        return mRootView.findViewById(viewId);
    }

    /**
     * 要实现复写该方法
     */
    protected void onToolbarDoubleClick() {
        log.i(" 双击了toolbar");
    }

    /**
     * 如果需要懒加载，逻辑写在这里,只被调用一次
     */
    protected void onLazyLoad() {
        log.i("懒加载...");
    }

    @Override
    public void onDestroy() {
        log.i(TAG + " onDestroy()");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (null != mUnBinder) {
            mUnBinder.unbind();
        }
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetWorkEvent event) {
        //log.i(" 网络状态发送变化");
    }

    @Override
    public void onRetry() {

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
            mLoadingDialog.show(self.getSupportFragmentManager(), TAG);
        }
    }

    @Override
    public void cancelLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isAdded()) {
            mLoadingDialog.cancel();
        }
    }

    @Override
    public void finish() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    /**
     * 接收处理Disposable对象
     *
     * @param disposable
     */
    protected void manageDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }
}
