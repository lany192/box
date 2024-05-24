package com.lany192.box.user.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaItem {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("subarea")
    private List<AreaItem> subarea;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaItem> getSubarea() {
        return subarea;
    }

    public void setSubarea(List<AreaItem> subarea) {
        this.subarea = subarea;
    }
}
