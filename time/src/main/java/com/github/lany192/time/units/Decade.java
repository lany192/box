package com.github.lany192.time.units;

import com.github.lany192.time.impl.ResourcesTimeUnit;

public class Decade extends ResourcesTimeUnit {

    public Decade() {
        setMillisPerUnit(315569259747L);
    }

    @Override
    protected String getResourceKeyPrefix() {
        return "Decade";
    }

}
