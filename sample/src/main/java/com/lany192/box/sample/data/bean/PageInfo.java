package com.lany192.box.sample.data.bean;

import com.github.lany192.arch.entity.Page;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PageInfo<T> implements Page<T> {
    @SerializedName(value = "pageSize", alternate = {"pageCount"})
    private int pageSize;
    @SerializedName(value = "pageNum", alternate = {"page", "curPage"})
    private int pageNum;
    @SerializedName(value = "list", alternate = {"data", "datas"})
    private List<T> list;
    @SerializedName(value = "over", alternate = {"hasNext"})
    private boolean over;

    @Override
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return !over;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getPageNum() {
        return pageNum;
    }
}
