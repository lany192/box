package cn.smallplants.client.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Checkable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.text.CheckTextView;
import com.github.lany192.text.IconTextView;

public class CoverTextView extends IconTextView implements Checkable {
    private CheckTextView.OnCheckChangeListener mListener;
    private boolean checked;

    public CoverTextView(@NonNull Context context) {
        this(context, null);
    }

    public CoverTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoverTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        setIcon(R.drawable.ico_image);
        if (this.checked) {
            setTextColor(Color.parseColor("#4E8AF1"));
            setIconTint(ColorStateList.valueOf(Color.parseColor("#4E8AF1")));
        } else {
            setTextColor(Color.parseColor("#80869D"));
            setIconTint(ColorStateList.valueOf(Color.parseColor("#80869D")));
        }
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }

    public void setOnCheckChangeListener(CheckTextView.OnCheckChangeListener listener) {
        this.mListener = listener;
    }

    public interface OnCheckChangeListener {
        void onChanged(boolean checked);
    }
}