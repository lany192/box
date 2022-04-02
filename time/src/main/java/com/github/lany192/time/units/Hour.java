package com.github.lany192.time.units;

import com.github.lany192.time.impl.ResourcesTimeUnit;

public class Hour extends ResourcesTimeUnit {

    public Hour() {
        setMillisPerUnit(1000L * 60L * 60L);
    }

    @Override
    protected String getResourceKeyPrefix() {
        return "Hour";
    }

}
