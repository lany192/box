package com.github.lany192.time.impl;

import com.github.lany192.time.TimeUnit;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public abstract class ResourcesTimeUnit implements TimeUnit {
    private static long ID = 0;
    private long id = 0;
    private long maxQuantity = 0;
    private long millisPerUnit = 1;

    public ResourcesTimeUnit() {
        this.id = ID++;
    }

    /**
     * Return the time-unit prefix to specify which value to load from the bundle.
     */
    abstract protected String getResourceKeyPrefix();

    /**
     * Return the name of the resource bundle from which this unit's format should be loaded.
     */
    protected final String getResourceBundleName() {
        return "com.github.lany192.time.i18n.Resources";
    }

    @Override
    public long getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    @Override
    public long getMillisPerUnit() {
        return millisPerUnit;
    }

    public void setMillisPerUnit(long millisPerUnit) {
        this.millisPerUnit = millisPerUnit;
    }

    @Override
    public boolean isPrecise() {
        return true;
    }

    @Override
    public String toString() {
        return getResourceKeyPrefix();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Long.hashCode(id);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResourcesTimeUnit other = (ResourcesTimeUnit) obj;
        if (maxQuantity != other.maxQuantity)
            return false;
        return millisPerUnit == other.millisPerUnit;
    }
}
