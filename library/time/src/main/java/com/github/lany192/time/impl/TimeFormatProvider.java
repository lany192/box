package com.github.lany192.time.impl;

import com.github.lany192.time.TimeFormat;
import com.github.lany192.time.TimeUnit;

/**
 * Produces time formats. Currently only to be used on Resource bundle implementations when used in
 * {@link ResourcesTimeFormat} instances..
 *
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public interface TimeFormatProvider {
    /**
     * Return the appropriate {@link TimeFormat} for the given {@link TimeUnit}
     */
    public TimeFormat getFormatFor(TimeUnit t);
}
