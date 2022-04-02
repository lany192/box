package com.github.lany192.edittext;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.github.lany192.view.R;


/**
 * 带删除功能的EditText
 */
public class ClearEditText extends AppCompatEditText implements OnFocusChangeListener, TextWatcher {
    private Drawable mLeftDrawable;
    private boolean hasFocus;
    @DrawableRes
    private int mLeftDrawableResId;
    private boolean mClearEnable = false;
    @DrawableRes
    private int mClearDrawableResId = R.drawable.ico_input_clear;

    private float mClearDrawableSize = dp2px(18);
    private float mLeftDrawableSize = dp2px(18);

    public ClearEditText(Context context) {
        super(context);
        init(null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ClearEditText);
            mClearDrawableResId = a.getResourceId(R.styleable.ClearEditText_clear_drawable, mClearDrawableResId);
            mClearDrawableSize = a.getDimension(R.styleable.ClearEditText_clear_size, dp2px(18));
            mLeftDrawableResId = a.getResourceId(R.styleable.ClearEditText_left_drawable, -1);
            mLeftDrawableSize = a.getDimension(R.styleable.ClearEditText_left_size, dp2px(18));
            a.recycle();
        }
        initClearIcon();
        initLeftIcon();
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
        setCompoundDrawablePadding(8);
    }

    private float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().getDisplayMetrics());
    }

    public void setClearDrawable(@DrawableRes int resId) {
        this.mClearDrawableResId = resId;
        invalidate();
    }

    private void initClearIcon() {
        Drawable clearDrawable = getCompoundDrawables()[2];
        if (clearDrawable == null) {
            clearDrawable = ContextCompat.getDrawable(getContext(), mClearDrawableResId);
        }
        clearDrawable.setBounds(0, 0, (int) mClearDrawableSize, (int) mClearDrawableSize);
        Drawable right = mClearEnable ? clearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    private void initLeftIcon() {
        if (mLeftDrawableResId > 0) {
            mLeftDrawable = getCompoundDrawables()[0];
            if (mLeftDrawable == null) {
                mLeftDrawable = ContextCompat.getDrawable(getContext(), mLeftDrawableResId);
            }
            mLeftDrawable.setBounds(0, 0, (int) mLeftDrawableSize, (int) mLeftDrawableSize);
        }
        Drawable left = mLeftDrawable != null ? mLeftDrawable : null;
        setCompoundDrawables(left, getCompoundDrawables()[1], getCompoundDrawables()[2], getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    public void setLeftIconResource(@DrawableRes int resId) {
        mLeftDrawableResId = resId;
        initLeftIcon();
    }

    private void setClearIconVisible(boolean visible) {
        this.mClearEnable = visible;
        initClearIcon();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (hasFocus) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 设置震动效果
     */
    public void setShakeAnimation() {
        Animation animation = new TranslateAnimation(0, 10, 0, 0);
        animation.setInterpolator(new CycleInterpolator(5));
        animation.setDuration(1000);
        this.setAnimation(animation);
    }
}
