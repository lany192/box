package com.github.lany192.time.units;

import com.github.lany192.time.impl.ResourcesTimeUnit;

public class Millisecond extends ResourcesTimeUnit {

    public Millisecond() {
        setMillisPerUnit(1);
    }

    @Override
    protected String getResourceKeyPrefix() {
        return "Millisecond";
    }

}
