package com.github.lany192.time.units;

import com.github.lany192.time.impl.ResourcesTimeUnit;

public class Year extends ResourcesTimeUnit {

    public Year() {
        setMillisPerUnit(2629743830L * 12L);
    }

    @Override
    protected String getResourceKeyPrefix() {
        return "Year";
    }

}
