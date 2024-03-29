package com.github.lany192.arch.items;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.github.lany192.arch.R;
import com.github.lany192.arch.utils.ListUtils;
import com.github.lany192.arch.viewmodel.LifecycleViewModel;
import com.github.lany192.utils.NetUtils;
import com.hjq.toast.Toaster;

import java.util.List;

public abstract class ItemsViewModel extends LifecycleViewModel {
    /**
     * 列表数据集合
     */
    private final ItemsLiveData liveData = new ItemsLiveData();
    /**
     * 列表状态监测
     */
    private final MutableLiveData<ListState> listState = new MutableLiveData<>();
    /**
     * 页码，从1开始
     */
    private int page = 1;

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        super.onCreate(owner);
        showLoadingView();
    }

    /**
     * 是否支持加载更多
     */
    public boolean loadMoreEnable() {
        return true;
    }

    /**
     * 是否支持下拉刷新
     */
    public boolean refreshEnable() {
        return true;
    }

    public int getPage() {
        return page;
    }

    public ItemsLiveData getItems() {
        return liveData;
    }

    public MutableLiveData<ListState> getListState() {
        return listState;
    }

    public void showListState(ListState listState) {
        this.listState.postValue(listState);
    }

    /**
     * 没有更多加载
     */
    public void moreLoadEnd() {
        listState.postValue(ListState.MORE_LOAD_END);
    }

    /**
     * 结束加载更多
     */
    public void moreLoadFinish() {
        listState.postValue(ListState.MORE_LOAD_FINISH);
    }

    /**
     * 结束刷新请求
     */
    public void refreshFinish() {
        listState.postValue(ListState.REFRESH_FINISH);
    }

    /**
     * 异常停止请求
     */
    public void requestError() {
        listState.postValue(ListState.ERROR);
    }

    /**
     * 重置数据
     *
     * @param items 数据集
     */
    public void resetItems(List<?> items) {
        this.liveData.setItems((List<Object>) items);
        if (ListUtils.isEmpty(items)) {
            showEmptyView();
        }
        showContentView();
    }

    /**
     * 添加数据
     *
     * @param items 数据集
     */
    public void addItems(List<?> items) {
        this.liveData.addItems((List<Object>) items);
        if (ListUtils.isEmpty(items)) {
            this.showListState(ListState.MORE_LOAD_END);
        }
    }

    /**
     * 刷新列表
     */
    public void onRefresh() {
        listState.postValue(ListState.REFRESHING);
        if (NetUtils.isAvailable()) {
            page = 1;
            request(true);
        } else {
            showNetworkView();
            listState.postValue(ListState.ERROR);
        }
    }

    /**
     * 加载更多
     */
    public void onLoadMore() {
        listState.postValue(ListState.MORE_LOADING);
        if (NetUtils.isAvailable()) {
            page += 1;
            request(false);
        } else {
            Toaster.show(R.string.default_network);
            listState.postValue(ListState.ERROR);
        }
    }

    public abstract void request(boolean refresh);

    /**
     * 懒加载
     */
    @CallSuper
    @Override
    public void onLazyLoad() {
        super.onLazyLoad();
        request(true);
    }
}
