package com.github.lany192.arch.items;

import androidx.lifecycle.MutableLiveData;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ItemsLiveData extends MutableLiveData<ItemsLiveData> {
    private final Logger.Builder log = XLog.tag(getClass().getName());
    private List<Object> items = new ArrayList<>();

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
        postValue(this);
    }

    public void addItems(List<Object> items) {
        this.items.addAll(items);
        postValue(this);
    }

    public void addItem(Object item) {
        this.items.add(item);
        postValue(this);
    }

    /**
     * items数据处理转换
     */
    public <R> void map(Function<Object, R> function) {
        this.items = items.stream().map(function).collect(Collectors.toList());
        postValue(this);
    }

    /**
     * 删除指定单个数据
     */
    public void remove(Object item) {
        items.remove(item);
        postValue(this);
    }

    /**
     * 删除指定多个数据
     */
    public void remove(List<Object> items) {
        this.items.removeAll(items);
        postValue(this);
    }

    /**
     * 过滤数据
     */
    public void filter(Predicate<Object> predicate) {
        log.i("过滤数据前：" + items.size());
        List<Object> tmp = items.stream().filter(predicate).collect(Collectors.toList());
        this.items.clear();
        this.items.addAll(tmp);
        log.i("过滤数据后：" + items.size());
        postValue(this);
    }
}
