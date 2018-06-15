package com.lany.box.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Keep;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.Field;
import java.util.List;

@Keep
public class RollPicker extends NumberPicker {
    private static final int DIVIDER_HEIGHT = 2;
    private static final int TEXT_SIZE = 14;
    @ColorInt
    private static final int TEXT_COLOR = Color.parseColor("#000000");
    @ColorInt
    private static final int DIVIDER_COLOR = Color.parseColor("#cccccc");

    public RollPicker(Context context) {
        super(context);
    }

    public RollPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RollPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    @Keep
    public void updateView(View view) {
        if (view instanceof EditText) {
            //这里修改字体的属性
            ((EditText) view).setTextColor(TEXT_COLOR);
            ((EditText) view).setTextSize(TEXT_SIZE);
            view.setPadding(0, 0, 0, 0);
        }
        setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        //设置分割线颜色
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field field : pickerFields) {
            if (field.getName().equals("mSelectionDivider")) {
                field.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    field.set(this, new ColorDrawable(DIVIDER_COLOR));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        // 分割线高度
        for (Field field : pickerFields) {
            if (field.getName().equals("mSelectionDividerHeight")) {
                field.setAccessible(true);
                try {
                    field.set(this, DIVIDER_HEIGHT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void setShowValues(List<String> list) {
        String[] values = new String[list.size()];
        list.toArray(values);
        super.setDisplayedValues(values);
    }
}
