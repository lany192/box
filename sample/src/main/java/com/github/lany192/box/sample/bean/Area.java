package com.github.lany192.box.sample.bean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Area {
    private long id;
    private String name;
    private List<Area> children;
}
