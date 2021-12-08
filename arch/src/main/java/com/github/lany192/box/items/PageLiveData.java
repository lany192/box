package com.github.lany192.box.items;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class PageLiveData extends MutableLiveData<PageLiveData> {
    private List<Object> items = new ArrayList<>();
    private boolean refresh;

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
        this.refresh = true;
        postValue(this);
    }

    public boolean isRefresh() {
        return refresh;
    }

    public void addItems(List<Object> items) {
        this.items.addAll(items);
        this.refresh = false;
        postValue(this);
    }
}
