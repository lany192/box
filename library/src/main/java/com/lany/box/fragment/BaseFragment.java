package com.lany.box.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.lany.box.R;
import com.lany.box.dialog.LoadingDialog;
import com.lany.box.event.NetWorkEvent;
import com.lany.box.interfaces.OnDoubleClickListener;
import com.lany.box.mvp.view.BaseView;
import com.lany.box.utils.PhoneUtils;
import com.lany.state.StateLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements StateLayout.OnRetryListener, BaseView {
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);
    protected FragmentActivity self;
    private StateLayout mStateLayout;
    private Unbinder mUnBinder;
    private LoadingDialog mLoadingDialog = null;
    private boolean isViewInit = false;
    private boolean isLazyLoaded = false;

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    protected boolean hasToolBar() {
        return false;
    }

    @LayoutRes
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_default_layout;
    }

    protected int getToolBarHeight() {
        return getResources().getDimensionPixelSize(R.dimen.actionbar_height);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        this.self = getActivity();
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
        RelativeLayout containView = new RelativeLayout(self);
        containView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout allView = new LinearLayout(self);
        allView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        allView.setOrientation(LinearLayout.VERTICAL);

        if (hasToolBar()) {
            View toolbar = inflater.inflate(getToolBarLayoutId(), null);
            toolbar.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
                @Override
                public void onDoubleClick(View view) {
                    onToolbarDoubleClick();
                }
            }));
            toolbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getToolBarHeight()));
            setPaddingSmart(toolbar);
            allView.addView(toolbar);
        }
        if (getLayoutId() != 0) {
            View contentView = inflater.inflate(getLayoutId(), null);
            mStateLayout = new StateLayout(getContext());
            mStateLayout.setOnRetryListener(this);
            mStateLayout.addView(contentView);
            allView.addView(mStateLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        } else {
            throw new IllegalArgumentException("getLayoutId() return 0 , you need a layout file resources");
        }
        mUnBinder = ButterKnife.bind(this, allView);
        init(savedInstanceState);
        containView.addView(allView);
        isViewInit = true;
        if (getUserVisibleHint() && !isLazyLoaded) {
            isLazyLoaded = true;
            onLazyLoad();
        }
        return containView;
    }

    public void setPaddingSmart(View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp != null && lp.height > 0) {
                lp.height += PhoneUtils.getStatusBarHeight(self);//增高
            }
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + PhoneUtils.getStatusBarHeight(self), view.getPaddingRight(), view.getPaddingBottom());
        }
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
        EventBus.getDefault().unregister(this);
        if (null != mUnBinder) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    @Subscribe
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

    public void finish() {
        getActivity().finish();
    }
}
