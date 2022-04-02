package com.github.lany192.time.i18n;

import com.github.lany192.time.Duration;
import com.github.lany192.time.TimeFormat;
import com.github.lany192.time.TimeUnit;
import com.github.lany192.time.format.SimpleTimeFormat;
import com.github.lany192.time.impl.TimeFormatProvider;
import com.github.lany192.time.units.Day;
import com.github.lany192.time.units.Hour;
import com.github.lany192.time.units.Millennium;
import com.github.lany192.time.units.Minute;
import com.github.lany192.time.units.Month;
import com.github.lany192.time.units.Week;
import com.github.lany192.time.units.Year;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Objects;
import java.util.ResourceBundle;

public class Resources_hr extends ListResourceBundle implements TimeFormatProvider {
    private static final Object[][] OBJECTS = new Object[][]{
            {"CenturyPattern", "%n %u"},
            {"CenturyFuturePrefix", "za "},
            {"CenturyFutureSuffix", ""},
            {"CenturyPastPrefix", ""},
            {"CenturyPastSuffix", " unatrag"},
            {"CenturySingularName", "stoljeće"},
            {"CenturyPluralName", "stoljeća"},
            {"DayPattern", "%n %u"},
            {"DayFuturePrefix", "za "},
            {"DayFutureSuffix", ""},
            {"DayPastPrefix", "prije "},
            {"DayPastSuffix", ""},
            {"DaySingularName", "dan"},
            {"DayPluralName", "dana"},
            {"DecadePattern", "%n %u"},
            {"DecadeFuturePrefix", "za "},
            {"DecadeFutureSuffix", ""},
            {"DecadePastPrefix", "prije "},
            {"DecadePastSuffix", ""},
            {"DecadeSingularName", "desetljeće"},
            {"DecadePluralName", "desetljeća"},
            {"HourPattern", "%n %u"},
            {"HourFuturePrefix", "za "},
            {"HourFutureSuffix", ""},
            {"HourPastPrefix", "prije "},
            {"HourPastSuffix", ""},
            {"HourSingularName", "sat"},
            {"HourPluralName", "sati"},
            {"JustNowPattern", "%u"},
            {"JustNowFuturePrefix", "za nekoliko trenutaka"},
            {"JustNowFutureSuffix", ""},
            {"JustNowPastPrefix", "prije nekoliko trenutaka"},
            {"JustNowPastSuffix", ""},
            {"JustNowSingularName", ""},
            {"JustNowPluralName", ""},
            {"MillenniumPattern", "%n %u"},
            {"MillenniumFuturePrefix", "za "},
            {"MillenniumFutureSuffix", ""},
            {"MillenniumPastPrefix", "prije "},
            {"MillenniumPastSuffix", ""},
            {"MillenniumSingularName", "tisućljeće"},
            {"MillenniumPluralName", "tisućljeća"},
            {"MillisecondPattern", "%n %u"},
            {"MillisecondFuturePrefix", "za "},
            {"MillisecondFutureSuffix", ""},
            {"MillisecondPastPrefix", "prije "},
            {"MillisecondPastSuffix", ""},
            {"MillisecondSingularName", "milisekunda"},
            {"MillisecondPluralName", "milisekunda"},
            {"MinutePattern", "%n %u"},
            {"MinuteFuturePrefix", "za "},
            {"MinuteFutureSuffix", ""},
            {"MinutePastPrefix", "prije "},
            {"MinutePastSuffix", ""},
            {"MinuteSingularName", "minuta"},
            {"MinutePluralName", "minuta"},
            {"MonthPattern", "%n %u"},
            {"MonthFuturePrefix", "za "},
            {"MonthFutureSuffix", ""},
            {"MonthPastPrefix", "prije "},
            {"MonthPastSuffix", ""},
            {"MonthSingularName", "mjesec"},
            {"MonthPluralName", "mjeseca"},
            {"SecondPattern", "%n %u"},
            {"SecondFuturePrefix", "za "},
            {"SecondFutureSuffix", ""},
            {"SecondPastPrefix", "prije "},
            {"SecondPastSuffix", ""},
            {"SecondSingularName", "sekunda"},
            {"SecondPluralName", "sekundi"},
            {"WeekPattern", "%n %u"},
            {"WeekFuturePrefix", "za "},
            {"WeekFutureSuffix", ""},
            {"WeekPastPrefix", "prije "},
            {"WeekPastSuffix", ""},
            {"WeekSingularName", "tjedan"},
            {"WeekPluralName", "tjedna"},
            {"YearPattern", "%n %u"},
            {"YearFuturePrefix", "za "},
            {"YearFutureSuffix", ""},
            {"YearPastPrefix", "prije "},
            {"YearPastSuffix", ""},
            {"YearSingularName", "godina"},
            {"YearPluralName", "godina"},
            {"AbstractTimeUnitPattern", ""},
            {"AbstractTimeUnitFuturePrefix", ""},
            {"AbstractTimeUnitFutureSuffix", ""},
            {"AbstractTimeUnitPastPrefix", ""},
            {"AbstractTimeUnitPastSuffix", ""},
            {"AbstractTimeUnitSingularName", ""},
            {"AbstractTimeUnitPluralName", ""}};

    @Override
    public Object[][] getContents() {
        return OBJECTS;
    }

    @Override
    public TimeFormat getFormatFor(final TimeUnit t) {
        if (t instanceof Minute) {
            return new HrTimeFormatBuilder("Minute").addNames("minutu", 1)
                    .addNames("minute", 4).addNames("minuta", Long.MAX_VALUE)
                    .build(this);
        } else if (t instanceof Hour) {
            return new HrTimeFormatBuilder("Hour").addNames("sat", 1)
                    .addNames("sata", 4).addNames("sati", Long.MAX_VALUE)
                    .build(this);
        } else if (t instanceof Day) {
            return new HrTimeFormatBuilder("Day").addNames("dan", 1)
                    .addNames("dana", 4).addNames("dana", Long.MAX_VALUE)
                    .build(this);
        } else if (t instanceof Week) {
            return new HrTimeFormatBuilder("Week").addNames("tjedan", 1)
                    .addNames("tjedna", 4).addNames("tjedana", Long.MAX_VALUE)
                    .build(this);
        } else if (t instanceof Month) {
            return new HrTimeFormatBuilder("Month").addNames("mjesec", 1)
                    .addNames("mjeseca", 4).addNames("mjeseci", Long.MAX_VALUE)
                    .build(this);
        } else if (t instanceof Year) {
            return new HrTimeFormatBuilder("Year").addNames("godinu", 1)
                    .addNames("godine", 4).addNames("godina", Long.MAX_VALUE)
                    .build(this);
        } else if (t instanceof Millennium) {
            return new HrTimeFormatBuilder("Millennium")
                    .addNames("tisućljeće", 1).addNames("tisućljeća", Long.MAX_VALUE)
                    .build(this);
        }
        // Don't override format for other time units
        return null;
    }

    private static class HrName implements Comparable<HrName> {

        private final boolean isFuture;

        private final Long threshold;

        private final String value;

        public HrName(final boolean isFuture, final String value, final Long threshold) {
            this.isFuture = isFuture;
            this.value = value;
            this.threshold = threshold;
        }

        @Override
        public int compareTo(final HrName o) {
            return threshold.compareTo(o.getThreshold());
        }

        public String get() {
            return value;
        }

        public long getThreshold() {
            return threshold;
        }

        public boolean isFuture() {
            return isFuture;
        }
    }

    private static class HrTimeFormat extends SimpleTimeFormat {

        private final List<HrName> futureNames = new ArrayList<HrName>();

        private final List<HrName> pastNames = new ArrayList<HrName>();

        public HrTimeFormat(final String resourceKeyPrefix, final ResourceBundle bundle, final Collection<HrName> names) {
            setPattern(bundle.getString(resourceKeyPrefix + "Pattern"));
            setFuturePrefix(bundle.getString(resourceKeyPrefix + "FuturePrefix"));
            setFutureSuffix(bundle.getString(resourceKeyPrefix + "FutureSuffix"));
            setPastPrefix(bundle.getString(resourceKeyPrefix + "PastPrefix"));
            setPastSuffix(bundle.getString(resourceKeyPrefix + "PastSuffix"));
            setSingularName(bundle.getString(resourceKeyPrefix + "SingularName"));
            setPluralName(bundle.getString(resourceKeyPrefix + "PluralName"));

            try {
                setFuturePluralName(bundle.getString(resourceKeyPrefix + "FuturePluralName"));
            } catch (final Exception e) {
            }
            try {
                setFutureSingularName(bundle.getString(resourceKeyPrefix + "FutureSingularName"));
            } catch (final Exception e) {
            }
            try {
                setPastPluralName(bundle.getString(resourceKeyPrefix + "PastPluralName"));
            } catch (final Exception e) {
            }
            try {
                setPastSingularName(bundle.getString(resourceKeyPrefix + "PastSingularName"));
            } catch (final Exception e) {
            }

            for (final HrName name : names) {
                if (name.isFuture()) {
                    futureNames.add(name);
                } else {
                    pastNames.add(name);
                }
            }
            Collections.sort(futureNames);
            Collections.sort(pastNames);
        }

        private String getGramaticallyCorrectName(final long quantity, final List<HrName> names) {
            for (final HrName name : names) {
                if (name.getThreshold() >= quantity) {
                    return name.get();
                }
            }
            throw new IllegalStateException("Invalid resource bundle configuration");
        }

        @Override
        protected String getGramaticallyCorrectName(final Duration d, final boolean round) {
            final long quantity = Math.abs(getQuantity(d, round));
            if (d.isInFuture()) {
                return getGramaticallyCorrectName(quantity, futureNames);
            }
            return getGramaticallyCorrectName(quantity, pastNames);
        }

    }

    private static class HrTimeFormatBuilder {

        private final List<HrName> names = new ArrayList<HrName>();

        private final String resourceKeyPrefix;

        HrTimeFormatBuilder(final String resourceKeyPrefix) {
            this.resourceKeyPrefix = resourceKeyPrefix;
        }

        private HrTimeFormatBuilder addName(final boolean isFuture, final String name, final long limit) {
            names.add(new HrName(isFuture, Objects.requireNonNull(name), limit));
            return this;
        }

        HrTimeFormatBuilder addNames(final String name, final long limit) {
            return addName(true, name, limit).addName(false, name, limit);
        }

        HrTimeFormat build(final ResourceBundle bundle) {
            return new HrTimeFormat(resourceKeyPrefix, bundle, names);
        }

    }

}
