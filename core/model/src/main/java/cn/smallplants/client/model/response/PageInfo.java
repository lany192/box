package cn.smallplants.client.model.response;

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
    @SerializedName(value = "hasNext", alternate = {"next"})
    private boolean next;

    @Override
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return next;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }
}
