package com.github.lany192.time.impl;

import com.github.lany192.time.Duration;
import com.github.lany192.time.TimeUnit;

import java.util.Objects;

public class DurationImpl implements Duration {
    private long quantity;
    private long delta;
    private TimeUnit unit;

    @Override
    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

    @Override
    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(final TimeUnit unit) {
        this.unit = unit;
    }

    @Override
    public long getDelta() {
        return delta;
    }

    public void setDelta(final long delta) {
        this.delta = delta;
    }

    @Override
    public boolean isInPast() {
        return getQuantity() < 0;
    }

    @Override
    public boolean isInFuture() {
        return !isInPast();
    }

    @Override
    public long getQuantityRounded(int tolerance) {
        long quantity = Math.abs(getQuantity());

        if (getDelta() != 0) {
            double threshold = Math
                    .abs(((double) getDelta() / (double) getUnit().getMillisPerUnit()) * 100);
            if (threshold > tolerance) {
                quantity = quantity + 1;
            }
        }
        return quantity;
    }

    @Override
    public String toString() {
        return "DurationImpl [" + quantity + " " + unit + ", delta=" + delta + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Long.hashCode(delta);
        result = prime * result + Long.hashCode(quantity);
        result = prime * result + Objects.hashCode(unit);
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
        DurationImpl other = (DurationImpl) obj;
        if (delta != other.delta)
            return false;
        if (quantity != other.quantity)
            return false;
        return Objects.equals(unit, other.unit);
    }
}
