package com.github.lany192.time.units;

import com.github.lany192.time.impl.ResourcesTimeUnit;

public class JustNow extends ResourcesTimeUnit {

    public JustNow() {
        setMaxQuantity(1000L * 60L);
    }

    @Override
    protected String getResourceKeyPrefix() {
        return "JustNow";
    }

    @Override
    public boolean isPrecise() {
        return false;
    }

}
