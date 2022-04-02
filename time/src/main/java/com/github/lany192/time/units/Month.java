package com.github.lany192.time.units;

import com.github.lany192.time.impl.ResourcesTimeUnit;

public class Month extends ResourcesTimeUnit {

    public Month() {
        setMillisPerUnit(2629743830L);
    }

    @Override
    protected String getResourceKeyPrefix() {
        return "Month";
    }

}
