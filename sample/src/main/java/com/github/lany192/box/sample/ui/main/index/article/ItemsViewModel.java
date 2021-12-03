package com.github.lany192.box.sample.ui.main.index.article;

import androidx.lifecycle.MutableLiveData;

import com.github.lany192.box.mvvm.LifecycleViewModel;
import com.github.lany192.multitype.delegate.Delegate;
import com.hjq.toast.ToastUtils;

import java.util.List;

public class ItemsViewModel extends LifecycleViewModel {
    private final MutableLiveData<List<Delegate>> items = new MutableLiveData<>();
    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public MutableLiveData<List<Delegate>> getItems() {
        return items;
    }

    public void setItems(List<Delegate> items) {
        this.items.postValue(items);
    }

    public void onRefresh() {
        ToastUtils.show("onRefresh");
    }

    public void onLoadMore() {
        ToastUtils.show("onLoadMore");
    }
}
