package com.github.lany192.arch.items;

import androidx.annotation.CallSuper;
import androidx.lifecycle.MutableLiveData;

import com.github.lany192.arch.utils.ContextUtils;
import com.github.lany192.arch.utils.ListUtils;
import com.github.lany192.arch.viewmodel.LifecycleViewModel;
import com.github.lany192.utils.NetUtils;

import java.util.List;

public abstract class ListViewModel extends LifecycleViewModel {
    /**
     * 列表数据集合
     */
    private final ListLiveData listLiveData = new ListLiveData();
    /**
     * 列表状态监测
     */
    private final MutableLiveData<ListState> listState = new MutableLiveData<>();
    /**
     * 页码，从1开始
     */
    private int page = 1;

    /**
     * 是否支持加载更多
     */
    public boolean loadMoreEnabled() {
        return true;
    }

    public int getPage() {
        return page;
    }

    public ListLiveData getItems() {
        return listLiveData;
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
     * 更多加载失败
     */
    public void moreLoadError() {
        listState.postValue(ListState.MORE_LOAD_ERROR);
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
    public void finishRequest() {
        listState.postValue(ListState.STOP_REQUEST);
        this.listLiveData.stopRequest();
    }

    /**
     * 重置数据
     *
     * @param items 数据集
     */
    @SuppressWarnings("unchecked")
    public void resetItems(List<?> items) {
        this.listLiveData.setItems((List<Object>) items);
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
    @SuppressWarnings("unchecked")
    public void addItems(List<?> items) {
        this.listLiveData.addItems((List<Object>) items);
        if (ListUtils.isEmpty(items)) {
            this.showListState(ListState.MORE_LOAD_END);
        }
    }

    /**
     * 刷新列表
     */
    public void onRefresh() {
        listState.postValue(ListState.REFRESHING);
        if (NetUtils.isAvailable(ContextUtils.getContext())) {
            page = 1;
            request(true);
        } else {
            showNetworkView();
            listState.postValue(ListState.STOP_REQUEST);
        }
    }

    /**
     * 加载更多
     */
    public void onLoadMore() {
        listState.postValue(ListState.MORE_LOADING);
        if (NetUtils.isAvailable(ContextUtils.getContext())) {
            page += 1;
            request(false);
        } else {
            showNetworkView();
            listState.postValue(ListState.STOP_REQUEST);
        }
    }

    public abstract void request(boolean refresh);

    /**
     * 懒加载
     */
    @CallSuper
    @Override
    protected void onLazyLoad() {
        request(true);
    }
}
