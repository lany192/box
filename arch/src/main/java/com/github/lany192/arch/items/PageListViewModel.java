package com.github.lany192.arch.items;

import androidx.annotation.CallSuper;
import androidx.lifecycle.MutableLiveData;

import com.github.lany192.arch.viewmodel.LifecycleViewModel;

import java.util.List;

public abstract class PageListViewModel extends LifecycleViewModel {
    /**
     * 列表数据集合
     */
    private final PageLiveData pageLiveData = new PageLiveData();
    /**
     * UI状态监测
     */
    private final MutableLiveData<UIState> uiState = new MutableLiveData<>(UIState.LOADING);

    /**
     * 页码，从1开始
     */
    private int page = 1;

    public int getPage() {
        return page;
    }

    public PageLiveData getItems() {
        return pageLiveData;
    }

    public MutableLiveData<UIState> getUiState() {
        return uiState;
    }

    public void changeState(UIState state){
        uiState.postValue(state);
    }

   /**
     * 结束刷新请求
     */
    public void finishRefresh() {
        uiState.postValue(UIState.REFRESH_FINISH);
    }

    /**
     * 结束加载更多
     */
    public void finishLoadMore() {
        uiState.postValue(UIState.MORE_LOAD_FINISH);
    }

    /**
     * 异常停止请求
     */
    public void finishRequest() {
        uiState.postValue(UIState.STOP_REQUEST);
        this.pageLiveData.stopRequest();
    }

    /**
     * 重置数据
     *
     * @param items 数据集
     */
    @SuppressWarnings("unchecked")
    public void resetItems(List<?> items) {
        this.pageLiveData.setItems((List<Object>) items);
    }

    /**
     * 添加数据
     *
     * @param items 数据集
     */
    @SuppressWarnings("unchecked")
    public void addItems(List<?> items) {
        this.pageLiveData.addItems((List<Object>) items);
    }

    /**
     * 刷新列表
     */
    public void onRefresh() {
        uiState.postValue(UIState.REFRESHING);
        page = 1;
        request(true);
    }

    /**
     * 加载更多
     */
    public void onLoadMore() {
        uiState.postValue(UIState.MORE_LOADING);
        page += 1;
        request(false);
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
