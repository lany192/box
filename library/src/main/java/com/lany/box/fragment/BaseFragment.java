package com.lany.box.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.lany.box.R;
import com.lany.box.config.FragmentConfig;
import com.lany.box.dialog.LoadingDialog;
import com.lany.box.event.NetWorkEvent;
import com.lany.box.interfaces.OnDoubleClickListener;
import com.lany.box.mvp.view.BaseView;
import com.lany.box.utils.DensityUtils;
import com.lany.box.utils.ViewUtils;
import com.lany.state.StateLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment implements StateLayout.OnRetryListener, BaseView {
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);
    protected FragmentActivity self;
    private StateLayout mStateLayout;
    private Unbinder mUnBinder;
    private LoadingDialog mLoadingDialog = null;
    private boolean isViewInit = false;
    private boolean isLazyLoaded = false;
    private RelativeLayout mRootView;
    private FragmentConfig config;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * 获取Fragment的界面配置
     *
     * @return FragmentConfig
     */
    @NonNull
    protected abstract FragmentConfig getConfig(FragmentConfig config);

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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && isViewInit && !isLazyLoaded) {
            isLazyLoaded = true;
            onLazyLoad();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = new RelativeLayout(self);
        mRootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        mStateLayout = new StateLayout(self);
        mStateLayout.setOnRetryListener(this);
        mStateLayout.addView(LayoutInflater.from(self).inflate(config.getLayoutId(), null));

        LayoutParams slp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (config.isHasToolbar()) {
            View toolbar = inflater.inflate(config.getToolBarLayoutId(), null);
            toolbar.setId(R.id.toolbar);
            toolbar.setOnTouchListener(new OnDoubleClickListener(view -> onToolbarDoubleClick()));
            toolbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, config.getToolbarHeight()));
            ViewUtils.setPaddingSmart(toolbar);
            slp.addRule(RelativeLayout.BELOW, toolbar.getId());
            mRootView.addView(toolbar);
        }

        mRootView.addView(mStateLayout, slp);
        mUnBinder = ButterKnife.bind(this, mRootView);
        init(savedInstanceState);
        isViewInit = true;
        if (getUserVisibleHint() && !isLazyLoaded) {
            isLazyLoaded = true;
            onLazyLoad();
        }
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
        log.i("onLazyInit懒加载");
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
        log.i(" 网络状态发送变化");
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

    protected void add(Disposable disposable) {
        compositeDisposable.add(disposable);
    }
}
