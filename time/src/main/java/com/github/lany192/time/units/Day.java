package com.github.lany192.time.units;

import com.github.lany192.time.impl.ResourcesTimeUnit;

public class Day extends ResourcesTimeUnit {

    public Day() {
        setMillisPerUnit(1000L * 60L * 60L * 24L);
    }

    @Override
    protected String getResourceKeyPrefix() {
        return "Day";
    }

}
