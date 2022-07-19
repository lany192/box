package com.github.lany192.arch.items;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class ItemsLiveData extends MutableLiveData<ItemsLiveData> {
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

    public void stopRequest() {
        this.refresh = false;
        postValue(this);
    }
}
