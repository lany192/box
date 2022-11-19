package com.github.lany192.dialog;

import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.ColorRes;
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
    @ColorRes
    private int mTitleColor = R.color.text_1level;
    private float titleSize = 18;
    private float mMsgTextSize = 16;
    @ColorRes
    private int mRightTextColor = 0;
    @ColorRes
    private int mLeftTextColor = 0;

    private MovementMethod movementMethod;

    @Override
    protected void init() {
        if (TextUtils.isEmpty(mTitle)) {
            getBinding().title.setVisibility(View.GONE);
        } else {
            getBinding().title.setText(mTitle);
            getBinding().title.setTextSize(titleSize);
            getBinding().title.setVisibility(View.VISIBLE);
            getBinding().title.setTextColorId(mTitleColor);
        }
        if (!TextUtils.isEmpty(mMessage)) {
            getBinding().content.setText(mMessage);
            getBinding().content.setGravity(gravity);
            getBinding().content.setTextSize(mMsgTextSize);
            getBinding().content.setTextColorId(R.color.text_2level);
            if (movementMethod != null) {
                getBinding().content.setMovementMethod(movementMethod);
            } else {
                getBinding().content.setMovementMethod(ScrollingMovementMethod.getInstance());
            }
        }
        if (isShowDivider) {
            getBinding().divider.setVisibility(View.VISIBLE);
        } else {
            getBinding().divider.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mRightText)) {
            getBinding().rightButton.setText(mRightText);
            getBinding().rightButton.setVisibility(View.VISIBLE);
            if (mRightTextColor != 0) {
                getBinding().rightButton.setTextColor(getColor(mRightTextColor));
            }
            getBinding().rightButton.setOnClickListener(v -> {
                cancel();
                if (null != mOnRightListener) {
                    mOnRightListener.onCallback();
                }
            });
        } else {
            getBinding().rightButton.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mLeftText)) {
            getBinding().leftButton.setText(mLeftText);
            getBinding().leftButton.setVisibility(View.VISIBLE);
            if (mLeftTextColor != 0) {
                getBinding().leftButton.setTextColor(getColor(mLeftTextColor));
            }
            getBinding().leftButton.setOnClickListener(v -> {
                cancel();
                if (null != mOnLeftListener) {
                    mOnLeftListener.onCallback();
                }
            });
        } else {
            getBinding().leftButton.setVisibility(View.GONE);
        }
    }

    public void setMovementMethod(MovementMethod movementMethod) {
        this.movementMethod = movementMethod;
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
    }

    public void setTitleColor(@ColorRes int colorIntId) {
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

    public void setRightTextColor(@ColorRes int colorIntId) {
        this.mRightTextColor = colorIntId;
    }

    public void setLeftTextColor(@ColorRes int leftTextColor) {
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