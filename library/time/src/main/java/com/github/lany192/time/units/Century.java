package com.github.lany192.time.units;

import com.github.lany192.time.impl.ResourcesTimeUnit;

public class Century extends ResourcesTimeUnit {
    public Century() {
        setMillisPerUnit(3155692597470L);
    }

    @Override
    protected String getResourceKeyPrefix() {
        return "Century";
    }
}
