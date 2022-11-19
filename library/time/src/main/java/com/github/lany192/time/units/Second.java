package com.github.lany192.time.units;

import com.github.lany192.time.impl.ResourcesTimeUnit;

public class Second extends ResourcesTimeUnit {

    public Second() {
        setMillisPerUnit(1000L);
    }

    @Override
    protected String getResourceKeyPrefix() {
        return "Second";
    }

}
