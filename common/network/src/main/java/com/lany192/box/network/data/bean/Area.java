package com.lany192.box.network.data.bean;

import java.util.List;

public class Area {
    private long id;
    private String name;
    private List<Area> subarea;

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

    public List<Area> getSubarea() {
        return subarea;
    }

    public void setSubarea(List<Area> subarea) {
        this.subarea = subarea;
    }
}
