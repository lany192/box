package com.github.lany192.time.i18n;

import com.github.lany192.time.Duration;
import com.github.lany192.time.TimeFormat;
import com.github.lany192.time.TimeUnit;
import com.github.lany192.time.impl.TimeFormatProvider;
import com.github.lany192.time.units.Century;
import com.github.lany192.time.units.Day;
import com.github.lany192.time.units.Decade;
import com.github.lany192.time.units.Hour;
import com.github.lany192.time.units.JustNow;
import com.github.lany192.time.units.Millennium;
import com.github.lany192.time.units.Millisecond;
import com.github.lany192.time.units.Minute;
import com.github.lany192.time.units.Month;
import com.github.lany192.time.units.Second;
import com.github.lany192.time.units.Week;
import com.github.lany192.time.units.Year;

import java.util.ListResourceBundle;

public class Resources_pl extends ListResourceBundle implements TimeFormatProvider {
    private static final Object[][] OBJECTS = new Object[0][0];

    private static final int tolerance = 50;

    // see http://translate.sourceforge.net/wiki/l10n/pluralforms
    private static final int polishPluralForms = 3;

    @Override
    public Object[][] getContents() {
        return OBJECTS;
    }

    @Override
    public TimeFormat getFormatFor(TimeUnit t) {
        if (t instanceof JustNow) {
            return new TimeFormat() {
                @Override
                public String format(Duration duration) {
                    return performFormat(duration);
                }

                @Override
                public String formatUnrounded(Duration duration) {
                    return performFormat(duration);
                }

                private String performFormat(Duration duration) {
                    if (duration.isInFuture()) {
                        return "za chwilę";
                    }
                    if (duration.isInPast()) {
                        return "przed chwilą";
                    }
                    return null;
                }

                @Override
                public String decorate(Duration duration, String time) {
                    return time;
                }

                @Override
                public String decorateUnrounded(Duration duration, String time) {
                    return time;
                }
            };
        } else if (t instanceof Century) {
            return new TimeFormatAided("wiek", "wieki", "wieków");
        } else if (t instanceof Day) {
            return new TimeFormatAided("dzień", "dni", "dni");
        } else if (t instanceof Decade) {
            return new TimeFormatAided("dekadę", "dekady", "dekad");
        } else if (t instanceof Hour) {
            return new TimeFormatAided("godzinę", "godziny", "godzin");
        } else if (t instanceof Millennium) {
            return new TimeFormatAided("milenium", "milenia", "mileniów");
        } else if (t instanceof Millisecond) {
            return new TimeFormatAided("milisekundę", "milisekundy", "milisekund");
        } else if (t instanceof Minute) {
            return new TimeFormatAided("minutę", "minuty", "minut");
        } else if (t instanceof Month) {
            return new TimeFormatAided("miesiąc", "miesiące", "miesięcy");
        } else if (t instanceof Second) {
            return new TimeFormatAided("sekundę", "sekundy", "sekund");
        } else if (t instanceof Week) {
            return new TimeFormatAided("tydzień", "tygodnie", "tygodni");
        } else if (t instanceof Year) {
            return new TimeFormatAided("rok", "lata", "lat");
        }
        return null; // error
    }

    private static class TimeFormatAided implements TimeFormat {
        private final String[] plurals;

        public TimeFormatAided(String... plurals) {
            if (plurals.length != polishPluralForms) {
                throw new IllegalArgumentException("Wrong plural forms number for Polish language!");
            }
            this.plurals = plurals;
        }

        @Override
        public String format(Duration duration) {
            long quantity = duration.getQuantityRounded(tolerance);
            return String.valueOf(quantity);
        }

        @Override
        public String formatUnrounded(Duration duration) {
            long quantity = Math.abs(duration.getQuantity());
            return String.valueOf(quantity);
        }

        @Override
        public String decorate(Duration duration, String time) {
            return performDecoration(
                    duration.isInPast(),
                    duration.isInFuture(),
                    duration.getQuantityRounded(tolerance),
                    time);
        }

        @Override
        public String decorateUnrounded(Duration duration, String time) {
            return performDecoration(
                    duration.isInPast(),
                    duration.isInFuture(),
                    Math.abs(duration.getQuantity()),
                    time);
        }

        private String performDecoration(boolean past, boolean future, long n, String time) {
            // a bit cryptic, yet well-tested
            // consider http://translate.sourceforge.net/wiki/l10n/pluralforms
            int pluralIdx = (n == 1 ? 0 : n % 10 >= 2 && n % 10 <= 4 && (n % 100 < 10 || n % 100 >= 20) ? 1 : 2);
            if (pluralIdx > polishPluralForms) {
                // impossible happening
                throw new IllegalStateException("Wrong plural index was calculated somehow for Polish language");
            }

            StringBuilder result = new StringBuilder();

            if (future) {
                result.append("za ");
            }

            result.append(time);
            result.append(' ');
            result.append(plurals[pluralIdx]);

            if (past) {
                result.append(" temu");
            }

            return result.toString();
        }
    }
}
