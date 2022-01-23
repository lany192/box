package com.github.lany192.dialog;

import android.graphics.Color;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.StringRes;

import com.github.lany192.dialog.databinding.DialogSimpleBinding;
import com.github.lany192.interfaces.OnSimpleListener;

//    SimpleDialog dialog = new SimpleDialog();
//    dialog.setTitle("提示");
//    dialog.setMessage("猜猜我是谁");
//    dialog.setRightBtn("确定", new OnSimpleListener() {
//        @Override
//        public void onCallback() {
//
//        }
//    });
//    dialog.setLeftBtn("取消", new OnSimpleListener() {
//        @Override
//        public void onCallback() {
//
//        }
//    });
//    dialog.show();

public class SimpleDialog extends BaseDialog<DialogSimpleBinding> {
    private OnSimpleListener mOnRightListener;
    private OnSimpleListener mOnLeftListener;
    private CharSequence mTitle;
    private CharSequence mMessage;
    private CharSequence mLeftText;
    private CharSequence mRightText;
    private boolean isShowDivider;
    private int gravity = Gravity.CENTER;
    @ColorInt
    private int mTitleColor = Color.BLACK;
    private float titleSize = 18;
    private float mMsgTextSize = 16;
    @ColorInt
    private int mRightTextColor = 0;
    @ColorInt
    private int mLeftTextColor = 0;

    private MovementMethod movementMethod;

    @Override
    protected void init() {
        if (TextUtils.isEmpty(mTitle)) {
            binding.title.setVisibility(View.GONE);
        } else {
            binding.title.setText(mTitle);
            binding.title.setTextSize(titleSize);
            binding.title.setVisibility(View.VISIBLE);
            binding.title.setTextColor(mTitleColor);
        }
        if (!TextUtils.isEmpty(mMessage)) {
            binding.content.setText(mMessage);
            binding.content.setGravity(gravity);
            binding.content.setTextSize(mMsgTextSize);
            if (movementMethod != null) {
                binding.content.setMovementMethod(movementMethod);
            } else {
                binding.content.setMovementMethod(ScrollingMovementMethod.getInstance());
            }
        }
        if (isShowDivider) {
            binding.divider.setVisibility(View.VISIBLE);
        } else {
            binding.divider.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mRightText)) {
            binding.rightButton.setText(mRightText);
            binding.rightButton.setVisibility(View.VISIBLE);
            if (mRightTextColor != 0) {
                binding.rightButton.setTextColor(mRightTextColor);
            }
            binding.rightButton.setOnClickListener(v -> {
                cancel();
                if (null != mOnRightListener) {
                    mOnRightListener.onCallback();
                }
            });
        } else {
            binding.rightButton.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mLeftText)) {
            binding.leftButton.setText(mLeftText);
            binding.leftButton.setVisibility(View.VISIBLE);
            if (mLeftTextColor != 0) {
                binding.leftButton.setTextColor(mLeftTextColor);
            }
            binding.leftButton.setOnClickListener(v -> {
                cancel();
                if (null != mOnLeftListener) {
                    mOnLeftListener.onCallback();
                }
            });
        } else {
            binding.leftButton.setVisibility(View.GONE);
        }
    }

    public void setMovementMethod(MovementMethod movementMethod) {
        this.movementMethod = movementMethod;
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
    }

    public void setTitleColor(@ColorInt int colorIntId) {
        this.mTitleColor = colorIntId;
    }

    public void setTitleSize(float titleSize) {
        this.titleSize = titleSize;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void setMessage(CharSequence message) {
        this.mMessage = message;
    }

    public void setMessageTextSize(float size) {
        this.mMsgTextSize = size;
    }

    public void setRightTextColor(@ColorInt int colorIntId) {
        this.mRightTextColor = colorIntId;
    }

    public void setLeftTextColor(@ColorInt int leftTextColor) {
        this.mLeftTextColor = leftTextColor;
    }

    public void setRightButton(@StringRes int rightStringRes, OnSimpleListener listener) {
        setRightButton(getString(rightStringRes), listener);
    }

    public void setLeftButton(@StringRes int leftStringRes, OnSimpleListener listener) {
        setLeftButton(getString(leftStringRes), listener);
    }

    public void setRightButton(CharSequence rightText, OnSimpleListener listener) {
        this.mRightText = rightText;
        this.mOnRightListener = listener;
    }

    public void setLeftButton(CharSequence leftText, OnSimpleListener listener) {
        this.mLeftText = leftText;
        this.mOnLeftListener = listener;
        this.isShowDivider = true;
    }

    public void setRightButton(CharSequence rightText) {
        this.mRightText = rightText;
    }

    public void setLeftButton(CharSequence leftText) {
        this.mLeftText = leftText;
        this.isShowDivider = true;
    }
}