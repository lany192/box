package com.github.lany192.time.i18n;

import com.github.lany192.time.Duration;
import com.github.lany192.time.TimeFormat;
import com.github.lany192.time.TimeUnit;
import com.github.lany192.time.format.SimpleTimeFormat;
import com.github.lany192.time.impl.TimeFormatProvider;
import com.github.lany192.time.units.Day;

import java.util.ListResourceBundle;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class Resources_fi extends ListResourceBundle implements TimeFormatProvider {

    private static final int tolerance = 50;

    private static Object[][] CONTENTS = new Object[][]{
            {"JustNowPattern", "%u"},
            {"JustNowPastSingularName", "hetki"},
            {"JustNowFutureSingularName", "hetken"},
            {"JustNowPastSuffix", "sitten"},
            {"JustNowFutureSuffix", "päästä"},
            {"MillisecondPattern", "%u"},
            {"MillisecondPluralPattern", "%n %u"},
            {"MillisecondPastSingularName", "millisekunti"},
            {"MillisecondPastPluralName", "millisekuntia"},
            {"MillisecondFutureSingularName", "millisekunnin"},
            {"MillisecondPastSuffix", "sitten"},
            {"MillisecondFutureSuffix", "päästä"},
            {"SecondPattern", "%u"},
            {"SecondPluralPattern", "%n %u"},
            {"SecondPastSingularName", "sekunti"},
            {"SecondPastPluralName", "sekuntia"},
            {"SecondFutureSingularName", "sekunnin"},
            {"SecondPastSuffix", "sitten"},
            {"SecondFutureSuffix", "päästä"},
            {"MinutePattern", "%u"},
            {"MinutePluralPattern", "%n %u"},
            {"MinutePastSingularName", "minuutti"},
            {"MinutePastPluralName", "minuuttia"},
            {"MinuteFutureSingularName", "minuutin"},
            {"MinutePastSuffix", "sitten"},
            {"MinuteFutureSuffix", "päästä"},
            {"HourPattern", "%u"},
            {"HourPluralPattern", "%n %u"},
            {"HourPastSingularName", "tunti"},
            {"HourPastPluralName", "tuntia"},
            {"HourFutureSingularName", "tunnin"},
            {"HourPastSuffix", "sitten"},
            {"HourFutureSuffix", "päästä"},
            {"DayPattern", "%u"},
            {"DayPluralPattern", "%n %u"},
            {"DayPastSingularName", "eilen"},
            {"DayPastPluralName", "päivää"},
            {"DayFutureSingularName", "huomenna"},
            {"DayFuturePluralName", "päivän"},
            {"DayPastSuffix", "sitten"},
            {"DayFutureSuffix", "päästä"},
            {"WeekPattern", "%u"},
            {"WeekPluralPattern", "%n %u"},
            {"WeekPastSingularName", "viikko"},
            {"WeekPastPluralName", "viikkoa"},
            {"WeekFutureSingularName", "viikon"},
            {"WeekFuturePluralName", "viikon"},
            {"WeekPastSuffix", "sitten"},
            {"WeekFutureSuffix", "päästä"},
            {"MonthPattern", "%u"},
            {"MonthPluralPattern", "%n %u"},
            {"MonthPastSingularName", "kuukausi"},
            {"MonthPastPluralName", "kuukautta"},
            {"MonthFutureSingularName", "kuukauden"},
            {"MonthPastSuffix", "sitten"},
            {"MonthFutureSuffix", "päästä"},
            {"YearPattern", "%u"},
            {"YearPluralPattern", "%n %u"},
            {"YearPastSingularName", "vuosi"},
            {"YearPastPluralName", "vuotta"},
            {"YearFutureSingularName", "vuoden"},
            {"YearPastSuffix", "sitten"},
            {"YearFutureSuffix", "päästä"},
            {"DecadePattern", "%u"},
            {"DecadePluralPattern", "%n %u"},
            {"DecadePastSingularName", "vuosikymmen"},
            {"DecadePastPluralName", "vuosikymmentä"},
            {"DecadeFutureSingularName", "vuosikymmenen"},
            {"DecadePastSuffix", "sitten"},
            {"DecadeFutureSuffix", "päästä"},
            {"CenturyPattern", "%u"},
            {"CenturyPluralPattern", "%n %u"},
            {"CenturyPastSingularName", "vuosisata"},
            {"CenturyPastPluralName", "vuosisataa"},
            {"CenturyFutureSingularName", "vuosisadan"},
            {"CenturyPastSuffix", "sitten"},
            {"CenturyFutureSuffix", "päästä"},
            {"MillenniumPattern", "%u"},
            {"MillenniumPluralPattern", "%n %u"},
            {"MillenniumPastSingularName", "vuosituhat"},
            {"MillenniumPastPluralName", "vuosituhatta"},
            {"MillenniumFutureSingularName", "vuosituhannen"},
            {"MillenniumPastSuffix", "sitten"},
            {"MillenniumFutureSuffix", "päästä"},
    };
    private final Map<TimeUnit, TimeFormat> formatMap = new ConcurrentHashMap<>();

    public Resources_fi() {
    }

    @Override
    public TimeFormat getFormatFor(TimeUnit t) {
        return formatMap.computeIfAbsent(t, unit -> new FiTimeFormat(this, unit));
    }

    @Override
    protected Object[][] getContents() {
        return CONTENTS;
    }

    private static class FiTimeFormat extends SimpleTimeFormat {
        private final ResourceBundle bundle;
        private String pastName = "";
        private String futureName = "";
        private String pastPluralName = "";
        private String futurePluralName = "";
        private String pluralPattern = "";

        public FiTimeFormat(final ResourceBundle rb, final TimeUnit unit) {
            super();
            this.bundle = rb;

            if (bundle.containsKey(getUnitName(unit) + "PastSingularName")) {
                this.setPastName(bundle.getString(getUnitName(unit) + "PastSingularName"))
                        .setFutureName(bundle.getString(getUnitName(unit) + "FutureSingularName"))
                        .setPastPluralName(bundle.getString(getUnitName(unit) + "PastSingularName"))
                        .setFuturePluralName(bundle.getString(getUnitName(unit) + "FutureSingularName"))
                        .setPluralPattern(bundle.getString(getUnitName(unit) + "Pattern"));

                if (bundle.containsKey(getUnitName(unit) + "PastPluralName")) {
                    this.setPastPluralName(bundle.getString(getUnitName(unit) + "PastPluralName"));
                }

                if (bundle.containsKey(getUnitName(unit) + "FuturePluralName")) {
                    this.setFuturePluralName(bundle.getString(getUnitName(unit) + "FuturePluralName"));
                }

                if (bundle.containsKey(getUnitName(unit) + "PluralPattern")) {
                    this.setPluralPattern(bundle.getString(getUnitName(unit) + "PluralPattern"));
                }

                this.setPattern(bundle.getString(getUnitName(unit) + "Pattern"))
                        .setPastSuffix(bundle.getString(getUnitName(unit) + "PastSuffix"))
                        .setFutureSuffix(bundle.getString(getUnitName(unit) + "FutureSuffix"))
                        .setFuturePrefix("")
                        .setPastPrefix("")
                        .setSingularName("")
                        .setPluralName("");
            }
        }

        public String getPastName() {
            return pastName;
        }

        public FiTimeFormat setPastName(String pastName) {
            this.pastName = pastName;
            return this;
        }

        public String getFutureName() {
            return futureName;
        }

        public FiTimeFormat setFutureName(String futureName) {
            this.futureName = futureName;
            return this;
        }

        public String getPastPluralName() {
            return pastPluralName;
        }

        public FiTimeFormat setPastPluralName(String pastName) {
            this.pastPluralName = pastName;
            return this;
        }

        public String getFuturePluralName() {
            return futurePluralName;
        }

        public FiTimeFormat setFuturePluralName(String futureName) {
            this.futurePluralName = futureName;
            return this;
        }

        public String getPluralPattern() {
            return pluralPattern;
        }

        public FiTimeFormat setPluralPattern(String pattern) {
            this.pluralPattern = pattern;
            return this;
        }

        @Override
        protected String getGramaticallyCorrectName(Duration d, boolean round) {
            String result = d.isInPast() ? getPastName() : getFutureName();
            if ((Math.abs(getQuantity(d, round)) == 0) || (Math.abs(getQuantity(d, round)) > 1)) {
                result = d.isInPast() ? getPastPluralName() : getFuturePluralName();
            }
            return result;
        }

        @Override
        protected String getPattern(long quantity) {
            if (Math.abs(quantity) == 1) {
                return getPattern();
            }
            return getPluralPattern();

        }

        @Override
        public String decorate(Duration duration, String time) {
            String result = "";
            if (duration.getUnit() instanceof Day && Math.abs(duration.getQuantityRounded(tolerance)) == 1) {
                result = time;
            } else {
                result = super.decorate(duration, time);
            }
            return result;
        }

        private String getUnitName(TimeUnit unit) {
            return unit.getClass().getSimpleName();
        }

    }

}
