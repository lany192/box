package com.github.lany192.text;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.view.R;

/**
 * 自定义CheckBox
 */
public class CheckTextView extends IconTextView implements Checkable {
    private OnCheckChangeListener mListener;
    private boolean checked;

    public CheckTextView(@NonNull Context context) {
        this(context, null);
    }

    public CheckTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(v -> toggle());
        showIcon();
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        showIcon();
        if (mListener != null) {
            mListener.onChanged(this.checked);
        }
    }

    private void showIcon() {
        if (this.checked) {
            setIcon(R.drawable.ico_check);
        } else {
            setIcon(R.drawable.ico_uncheck);
        }
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }

    public void setOnCheckChangeListener(OnCheckChangeListener listener) {
        this.mListener = listener;
    }

    public interface OnCheckChangeListener {
        void onChanged(boolean checked);
    }
}
