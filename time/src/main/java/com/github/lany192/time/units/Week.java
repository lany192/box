package com.github.lany192.time.units;

import com.github.lany192.time.impl.ResourcesTimeUnit;

public class Week extends ResourcesTimeUnit {

    public Week() {
        setMillisPerUnit(1000L * 60L * 60L * 24L * 7L);
    }

    @Override
    protected String getResourceKeyPrefix() {
        return "Week";
    }

}
