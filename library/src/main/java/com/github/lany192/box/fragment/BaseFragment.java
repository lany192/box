package com.github.lany192.box.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.R;
import com.github.lany192.box.dialog.LoadingDialog;
import com.github.lany192.box.event.NetWorkEvent;
import com.github.lany192.box.interfaces.OnDoubleClickListener;
import com.github.lany192.box.mvp.BaseView;
import com.github.lany192.box.utils.ViewUtils;
import com.github.lany192.view.StateLayout;
import com.github.mmin18.widget.RealtimeBlurView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseFragment extends Fragment implements StateLayout.OnRetryListener, BaseView {
    protected final String TAG = this.getClass().getName();
    protected Logger.Builder log = XLog.tag(TAG);
    private StateLayout stateLayout;
    private Unbinder unbinder;
    private LoadingDialog loadingDialog;
    /**
     * 是否执行过懒加载
     */
    private boolean isLazyLoaded;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    /**
     * 本界面用户是否可见
     */
    private boolean userVisible;

    /**
     * 配置信息
     */
    @NonNull
    public abstract FragmentConfig getConfig();

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
    }

    @Override
    public void onResume() {
        super.onResume();
        userVisible = true;
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
        RelativeLayout rootView = new RelativeLayout(getContext());
        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        stateLayout = new StateLayout(getContext());
        stateLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        stateLayout.setOnRetryListener(this);
        stateLayout.addView(inflater.inflate(getConfig().getLayoutId(), null));
        rootView.addView(stateLayout);

        if (getConfig().isHasToolbar()) {
            if (getConfig().isToolbarBlur()) {
                RealtimeBlurView blurView = new RealtimeBlurView(getContext(), null);
                blurView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getConfig().getToolbarHeight()));
                ViewUtils.setPaddingSmart(blurView);
                rootView.addView(blurView);
            }
            View toolbar = inflater.inflate(getConfig().getToolBarLayoutId(), null);
            toolbar.setOnTouchListener(new OnDoubleClickListener(view -> onToolbarDoubleClick()));
            toolbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getConfig().getToolbarHeight()));
            ViewUtils.setPaddingSmart(toolbar);
            rootView.addView(toolbar);
        }

        unbinder = ButterKnife.bind(this, rootView);
        init(savedInstanceState);
        return rootView;
    }

    public <T extends View> T getView(@IdRes int viewId) {
        return getView().findViewById(viewId);
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
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (null != unbinder) {
            unbinder.unbind();
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

    @Override
    public void showLoadingDialog() {
        showLoadingDialog(getString(R.string.loading));
    }

    @Override
    public void showLoadingDialog(CharSequence message) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog();
        }
        loadingDialog.setMessage(message);
        if (!loadingDialog.isAdded()) {
            loadingDialog.show(getParentFragmentManager(), TAG);
        }
    }

    @Override
    public void cancelLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isAdded()) {
            loadingDialog.cancel();
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
