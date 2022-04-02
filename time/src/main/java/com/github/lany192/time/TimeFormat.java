package com.github.lany192.time;

/**
 * Format a Duration object.
 *
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public interface TimeFormat {
    /**
     * Given a populated {@link Duration} object. Apply formatting (with rounding) and output the result.
     *
     * @param The original {@link Duration} instance from which the time string should be decorated.
     */
    public abstract String format(final Duration duration);

    /**
     * Given a populated {@link Duration} object. Apply formatting (without rounding) and output the result.
     *
     * @param The original {@link Duration} instance from which the time string should be decorated.
     */
    public String formatUnrounded(Duration duration);

    /**
     * Decorate with past or future prefix/suffix (with rounding)
     *
     * @param duration The original {@link Duration} instance from which the time string should be decorated.
     * @param time     The formatted time string.
     */
    public String decorate(Duration duration, String time);

    /**
     * Decorate with past or future prefix/suffix (without rounding)
     *
     * @param duration The original {@link Duration} instance from which the time string should be decorated.
     * @param time     The formatted time string.
     */
    public String decorateUnrounded(Duration duration, String time);

}