package com.github.lany192.box.items;

import androidx.annotation.CallSuper;
import androidx.lifecycle.MutableLiveData;

import com.github.lany192.box.mvvm.LifecycleViewModel;

import java.util.List;

public abstract class ItemsViewModel extends LifecycleViewModel {
    private final ItemsLiveData itemsLiveData = new ItemsLiveData();
    private final MutableLiveData<Boolean> refreshState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadMoreState = new MutableLiveData<>();
    private int page = 1;

    public int getPage() {
        return page;
    }

    public ItemsLiveData getItems() {
        return itemsLiveData;
    }

    public MutableLiveData<Boolean> getRefreshState() {
        return refreshState;
    }

    public MutableLiveData<Boolean> getLoadMoreState() {
        return loadMoreState;
    }

    public void finishRefresh() {
        this.refreshState.postValue(false);
    }

    public void finishLoadMore() {
        this.loadMoreState.postValue(false);
    }

    public void stopRequest() {
        this.refreshState.postValue(false);
        this.loadMoreState.postValue(false);
    }

    public void resetItems(List<?> items) {
        this.itemsLiveData.setItems((List<Object>) items);
    }

    public void addItems(List<?> items) {
        this.itemsLiveData.addItems((List<Object>) items);
    }

    public void onRefresh() {
        refreshState.postValue(true);
        page = 1;
        request(true);
    }

    public void onLoadMore() {
        loadMoreState.postValue(true);
        page += 1;
        request(false);
    }

    public abstract void request(boolean refresh);

    @CallSuper
    @Override
    protected void onLazyLoad() {
        request(true);
    }
}
