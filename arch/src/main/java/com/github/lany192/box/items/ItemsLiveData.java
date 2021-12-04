package com.github.lany192.box.items;

import androidx.lifecycle.MutableLiveData;

import com.github.lany192.multitype.delegate.ViewDelegate;

import java.util.ArrayList;
import java.util.List;

public class ItemsLiveData extends MutableLiveData<ItemsLiveData> {
    private List<ViewDelegate> items = new ArrayList<>();
    private boolean refresh;

    public List<ViewDelegate> getItems() {
        return items;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public void setItems(List<ViewDelegate> items) {
        this.items = items;
        this.refresh = true;
        postValue(this);
    }

    public void addItems(List<ViewDelegate> items) {
        this.items.addAll(items);
        this.refresh = false;
        postValue(this);
    }
}
