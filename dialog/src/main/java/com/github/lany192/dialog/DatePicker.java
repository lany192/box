package com.github.lany192.dialog;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.shawnlin.numberpicker.NumberPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/***
 * 自定义日期选择器
 */
public class DatePicker extends FrameLayout {
    private final LinearLayout mPickers;

    private final NumberPicker dayPicker;
    private final NumberPicker monthPicker;
    private final NumberPicker yearPicker;
    private Locale mCurrentLocale;
    private OnChangedListener listener;
    private int mNumberOfMonths;

    private Calendar mTempDate;
    private Calendar minCalendar;
    private Calendar maxCalendar;
    private Calendar currentCalendar;

    private boolean mIsEnabled = true;

    public DatePicker(Context context) {
        this(context, null);
    }

    public DatePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DatePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCurrentLocale(Locale.getDefault());

        int startYear = 1900;
        int endYear = 2100;
        String minDate = "01/01/2022";
        String maxDate = "01/01/2122";

        LayoutInflater.from(getContext()).inflate(R.layout.view_date_picker, this);

        NumberPicker.OnValueChangeListener onChangeListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mTempDate.setTimeInMillis(currentCalendar.getTimeInMillis());
                if (picker == dayPicker) {
                    int maxDayOfMonth = mTempDate.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (oldVal == maxDayOfMonth && newVal == 1) {
                        mTempDate.add(Calendar.DAY_OF_MONTH, 1);
                    } else if (oldVal == 1 && newVal == maxDayOfMonth) {
                        mTempDate.add(Calendar.DAY_OF_MONTH, -1);
                    } else {
                        mTempDate.add(Calendar.DAY_OF_MONTH, newVal - oldVal);
                    }
                } else if (picker == monthPicker) {
                    if (oldVal == 12 && newVal == 1) {
                        mTempDate.add(Calendar.MONTH, 1);
                    } else if (oldVal == 1 && newVal == 12) {
                        mTempDate.add(Calendar.MONTH, -1);
                    } else {
                        mTempDate.add(Calendar.MONTH, newVal - oldVal);
                    }
                } else if (picker == yearPicker) {
                    mTempDate.set(Calendar.YEAR, newVal);
                } else {
                    throw new IllegalArgumentException();
                }
                setDate(mTempDate.get(Calendar.YEAR), mTempDate.get(Calendar.MONTH), mTempDate.get(Calendar.DAY_OF_MONTH));
                updatePickers();
                notifyDateChanged();
            }
        };

        mPickers = findViewById(R.id.pickers);
        // day
        dayPicker = findViewById(R.id.day);
        dayPicker.setOnLongPressUpdateInterval(200);
        dayPicker.setOnValueChangedListener(onChangeListener);

        // month
        monthPicker = findViewById(R.id.month);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(mNumberOfMonths);
        monthPicker.setOnLongPressUpdateInterval(200);
        monthPicker.setOnValueChangedListener(onChangeListener);

        // year
        yearPicker = findViewById(R.id.year);
        yearPicker.setOnLongPressUpdateInterval(200);
        yearPicker.setOnValueChangedListener(onChangeListener);

        mTempDate.clear();
        if (!TextUtils.isEmpty(minDate)) {
            if (!parseDate(minDate, mTempDate)) {
                mTempDate.set(startYear, 0, 1);
            }
        } else {
            mTempDate.set(startYear, 0, 1);
        }
        setMinDate(mTempDate.getTime());

        mTempDate.clear();
        if (!TextUtils.isEmpty(maxDate)) {
            if (!parseDate(maxDate, mTempDate)) {
                mTempDate.set(endYear, 11, 31);
            }
        } else {
            mTempDate.set(endYear, 11, 31);
        }
        setMaxDate(mTempDate.getTime());

        currentCalendar.setTimeInMillis(System.currentTimeMillis());
        init(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH));

        reorderPickers();

        setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
    }

    public void setMinDate(Date minDate) {
        mTempDate.setTime(minDate);
        if (mTempDate.get(Calendar.YEAR) == minCalendar.get(Calendar.YEAR)
                && mTempDate.get(Calendar.DAY_OF_YEAR) != minCalendar.get(Calendar.DAY_OF_YEAR)) {
            return;
        }
        minCalendar.setTime(minDate);
        if (currentCalendar.before(minCalendar)) {
            currentCalendar.setTime(minCalendar.getTime());
        }
        updatePickers();
    }

    public void setMaxDate(Date maxDate) {
        mTempDate.setTime(maxDate);
        if (mTempDate.get(Calendar.YEAR) == maxCalendar.get(Calendar.YEAR)
                && mTempDate.get(Calendar.DAY_OF_YEAR) != maxCalendar
                .get(Calendar.DAY_OF_YEAR)) {
            return;
        }
        maxCalendar.setTime(maxDate);
        if (currentCalendar.after(maxCalendar)) {
            currentCalendar.setTime(maxCalendar.getTime());
        }
        updatePickers();
    }

    @Override
    public boolean isEnabled() {
        return mIsEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (mIsEnabled == enabled) {
            return;
        }
        super.setEnabled(enabled);
        dayPicker.setEnabled(enabled);
        monthPicker.setEnabled(enabled);
        yearPicker.setEnabled(enabled);
        mIsEnabled = enabled;
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        onPopulateAccessibilityEvent(event);
        return true;
    }

    @Override
    public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
        super.onPopulateAccessibilityEvent(event);
        final int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR;
        String selectedDateUtterance = DateUtils.formatDateTime(getContext(), currentCalendar.getTimeInMillis(), flags);
        event.getText().add(selectedDateUtterance);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(DatePicker.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(DatePicker.class.getName());
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setCurrentLocale(newConfig.locale);
    }

    private void setCurrentLocale(Locale locale) {
        if (locale.equals(mCurrentLocale)) {
            return;
        }
        mCurrentLocale = locale;

        mTempDate = getCalendarForLocale(mTempDate, locale);
        minCalendar = getCalendarForLocale(minCalendar, locale);
        maxCalendar = getCalendarForLocale(maxCalendar, locale);
        currentCalendar = getCalendarForLocale(currentCalendar, locale);

        mNumberOfMonths = mTempDate.getActualMaximum(Calendar.MONTH) + 1;
    }

    private Calendar getCalendarForLocale(Calendar oldCalendar, Locale locale) {
        if (oldCalendar == null) {
            return Calendar.getInstance(locale);
        } else {
            final long currentTimeMillis = oldCalendar.getTimeInMillis();
            Calendar newCalendar = Calendar.getInstance(locale);
            newCalendar.setTimeInMillis(currentTimeMillis);
            return newCalendar;
        }
    }

    private void reorderPickers() {
        mPickers.removeAllViews();
        char[] order;
        try {
            order = DateFormat.getDateFormatOrder(getContext());
        } catch (IllegalArgumentException expected) {
            order = new char[0];
        }
        for (char c : order) {
            switch (c) {
                case 'd':
                    mPickers.addView(dayPicker);
                    break;
                case 'M':
                    mPickers.addView(monthPicker);
                    break;
                case 'y':
                    mPickers.addView(yearPicker);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public void updateDate(int year, int month, int dayOfMonth) {
        setDate(year, month, dayOfMonth);
        updatePickers();
        notifyDateChanged();
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, getYear(), getMonth(), getDayOfMonth());
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setDate(ss.mYear, ss.mMonth, ss.mDay);
        updatePickers();
    }

    public void init(int year, int monthOfYear, int dayOfMonth) {
        setDate(year, monthOfYear, dayOfMonth);
        updatePickers();
    }

    private boolean parseDate(String date, Calendar outDate) {
        try {
            SimpleDateFormat mDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            outDate.setTime(Objects.requireNonNull(mDateFormat.parse(date)));
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void setDate(int year, int month, int dayOfMonth) {
        currentCalendar.set(year, month, dayOfMonth);
        if (currentCalendar.before(minCalendar)) {
            currentCalendar.setTimeInMillis(minCalendar.getTimeInMillis());
        } else if (currentCalendar.after(maxCalendar)) {
            currentCalendar.setTimeInMillis(maxCalendar.getTimeInMillis());
        }
    }

    private void updatePickers() {
        if (currentCalendar.get(Calendar.YEAR) == minCalendar.get(Calendar.YEAR)) {
            if (currentCalendar.get(Calendar.MONDAY) == minCalendar.get(Calendar.MONDAY)) {
                monthPicker.setWrapSelectorWheel(false);
                monthPicker.setMinValue(minCalendar.get(Calendar.MONTH) + 1);
                monthPicker.setMaxValue(minCalendar.getActualMaximum(Calendar.MONTH) + 1);


                if (currentCalendar.get(Calendar.DAY_OF_MONTH) == minCalendar.get(Calendar.DAY_OF_MONTH)) {
                    dayPicker.setWrapSelectorWheel(false);
                    dayPicker.setMinValue(minCalendar.get(Calendar.DAY_OF_MONTH));
                    dayPicker.setMaxValue(minCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

                } else {
                    dayPicker.setWrapSelectorWheel(true);
                    dayPicker.setMinValue(1);
                    dayPicker.setMaxValue(currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                }
            } else {
                dayPicker.setWrapSelectorWheel(true);
                dayPicker.setMinValue(1);
                dayPicker.setMaxValue(currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

                monthPicker.setWrapSelectorWheel(true);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);

            }
        } else if (currentCalendar.get(Calendar.YEAR) == maxCalendar.get(Calendar.YEAR)) {
            if (currentCalendar.get(Calendar.MONDAY) == maxCalendar.get(Calendar.MONDAY)) {
                monthPicker.setWrapSelectorWheel(false);
                monthPicker.setMinValue(maxCalendar.getActualMinimum(Calendar.MONTH) + 1);
                monthPicker.setMaxValue(maxCalendar.get(Calendar.MONTH) + 1);

                if (currentCalendar.get(Calendar.DAY_OF_MONTH) == maxCalendar.get(Calendar.DAY_OF_MONTH)) {
                    dayPicker.setWrapSelectorWheel(false);
                    dayPicker.setMinValue(maxCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                    dayPicker.setMaxValue(maxCalendar.get(Calendar.DAY_OF_MONTH));
                } else {
                    dayPicker.setWrapSelectorWheel(true);
                    dayPicker.setMinValue(1);
                    dayPicker.setMaxValue(currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                }
            } else {
                dayPicker.setWrapSelectorWheel(true);
                dayPicker.setMinValue(1);
                dayPicker.setMaxValue(currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

                monthPicker.setWrapSelectorWheel(true);
                monthPicker.setDisplayedValues(null);
                monthPicker.setMinValue(1);
                monthPicker.setMaxValue(12);
            }
        } else {
            dayPicker.setWrapSelectorWheel(true);
            dayPicker.setMinValue(1);
            dayPicker.setMaxValue(currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

            monthPicker.setWrapSelectorWheel(true);
            monthPicker.setMinValue(1);
            monthPicker.setMaxValue(12);
        }

        yearPicker.setWrapSelectorWheel(false);
        yearPicker.setMinValue(minCalendar.get(Calendar.YEAR));
        yearPicker.setMaxValue(maxCalendar.get(Calendar.YEAR));

        yearPicker.setValue(currentCalendar.get(Calendar.YEAR));
        monthPicker.setValue(currentCalendar.get(Calendar.MONTH) + 1);
        dayPicker.setValue(currentCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public int getYear() {
        return currentCalendar.get(Calendar.YEAR);
    }

    public int getMonth() {
        return currentCalendar.get(Calendar.MONTH);
    }

    public int getDayOfMonth() {
        return currentCalendar.get(Calendar.DAY_OF_MONTH);
    }

    private void notifyDateChanged() {
        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
        if (listener != null) {
            listener.onChanged(this, getYear(), getMonth(), getDayOfMonth());
        }
    }

    public void setOnValueChangedListener(OnChangedListener listener) {
        this.listener = listener;
    }

    public void setYearFormatter(String formatter) {
        yearPicker.setFormatter(formatter);
    }

    public void setMonthFormatter(String formatter) {
        monthPicker.setFormatter(formatter);
    }

    public void setDayFormatter(String formatter) {
        dayPicker.setFormatter(formatter);
    }

    public interface OnChangedListener {

        void onChanged(DatePicker picker, int year, int monthOfYear, int dayOfMonth);
    }

    private static class SavedState extends BaseSavedState {

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        private final int mYear;
        private final int mMonth;
        private final int mDay;

        private SavedState(Parcelable superState, int year, int month, int day) {
            super(superState);
            mYear = year;
            mMonth = month;
            mDay = day;
        }

        private SavedState(Parcel in) {
            super(in);
            mYear = in.readInt();
            mMonth = in.readInt();
            mDay = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(mYear);
            dest.writeInt(mMonth);
            dest.writeInt(mDay);
        }
    }
}
