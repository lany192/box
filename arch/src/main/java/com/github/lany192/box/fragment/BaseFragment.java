package com.github.lany192.box.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import com.github.lany192.box.R;
import com.github.lany192.box.event.NetWorkEvent;
import com.github.lany192.box.interfaces.OnDoubleClickListener;
import com.github.lany192.box.mvp.BaseView;
import com.github.lany192.box.utils.PhoneUtils;
import com.github.lany192.box.utils.ViewUtils;
import com.github.lany192.utils.DensityUtils;
import com.github.lany192.view.StateLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseFragment extends ViewModelFragment implements StateLayout.OnRetryListener, BaseView {
    private StateLayout stateLayout;
    private CompositeDisposable compositeDisposable;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout rootView = new RelativeLayout(getContext());
        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        stateLayout = new StateLayout(getContext());
        stateLayout.setLayoutParams(layoutParams);
        stateLayout.setOnRetryListener(this);
        stateLayout.addView(inflater.inflate(getConfig().getLayoutId(), null));
        rootView.addView(stateLayout);

        if (getConfig().hasToolbar()) {
            View toolbar = inflater.inflate(getConfig().getToolBarLayoutId() == 0 ? R.layout.toolbar_default : getConfig().getToolBarLayoutId(), null);
            toolbar.setId(R.id.toolbar);
            toolbar.setOnTouchListener(new OnDoubleClickListener(view -> onToolbarDoubleClick()));
            toolbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getConfig().getToolbarHeight() == 0 ? DensityUtils.dp2px(48) : getConfig().getToolbarHeight()));
            ViewUtils.setPaddingSmart(toolbar);
            rootView.addView(toolbar);

            if (!getConfig().isCoverStyle()) {
                layoutParams.addRule(RelativeLayout.BELOW, toolbar.getId());
                stateLayout.setLayoutParams(layoutParams);
            }
        }
        init(savedInstanceState);
        return rootView;
    }

    public int getToolbarHeight() {
        if (getConfig().hasToolbar()) {
            return getConfig().getToolbarHeight() + PhoneUtils.getStatusBarHeight();
        }
        return 0;
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

    /**
     * 处理disposable
     */
    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
}
