package com.github.lany192.box.sample.ui.main.index.article;

import androidx.lifecycle.MutableLiveData;

import com.github.lany192.multitype.delegate.Delegate;

import java.util.ArrayList;
import java.util.List;

public class ItemsLiveData extends MutableLiveData<ItemsLiveData> {
    private List<Delegate> items = new ArrayList<>();

    public List<Delegate> getItems() {
        return items;
    }

    public void setItems(List<Delegate> items) {
        this.items = items;
        postValue(this);
    }

    public void addItems(List<Delegate> items) {
        this.items.addAll(items);
        postValue(this);
    }
}
