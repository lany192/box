package com.github.lany192.time.units;

import com.github.lany192.time.impl.ResourcesTimeUnit;

public class Minute extends ResourcesTimeUnit {

    public Minute() {
        setMillisPerUnit(1000L * 60L);
    }

    @Override
    protected String getResourceKeyPrefix() {
        return "Minute";
    }

}
