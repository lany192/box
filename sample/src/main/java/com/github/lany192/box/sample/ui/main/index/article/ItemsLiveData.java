package com.github.lany192.box.sample.ui.main.index.article;

import androidx.lifecycle.MutableLiveData;

import com.github.lany192.multitype.delegate.Delegate;

import java.util.ArrayList;
import java.util.List;

public class ItemsLiveData extends MutableLiveData<ItemsLiveData> {
    private List<Delegate> items = new ArrayList<>();
    private boolean refresh;

    public List<Delegate> getItems() {
        return items;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public void setItems(List<Delegate> items) {
        this.items = items;
        this.refresh = true;
        postValue(this);
    }

    public void addItems(List<Delegate> items) {
        this.items.addAll(items);
        this.refresh = false;
        postValue(this);
    }
}
