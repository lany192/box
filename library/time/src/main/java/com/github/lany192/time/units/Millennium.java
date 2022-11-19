package com.github.lany192.time.units;

import com.github.lany192.time.impl.ResourcesTimeUnit;

public class Millennium extends ResourcesTimeUnit {

    public Millennium() {
        setMillisPerUnit(31556926000000L);
    }

    @Override
    protected String getResourceKeyPrefix() {
        return "Millennium";
    }

}
