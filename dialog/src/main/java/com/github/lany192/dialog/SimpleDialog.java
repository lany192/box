package com.github.lany192.dialog;

import android.graphics.Color;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.StringRes;

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
//    dialog.show(this);

public class SimpleDialog extends BaseDialog {
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
    private float titleSize = 17;
    private float mMsgTextSize = 15;
    @ColorInt
    private int mRightTextColor = 0;
    @ColorInt
    private int mLeftTextColor = 0;

    private MovementMethod movementMethod;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_simple;
    }

    @Override
    protected void init() {
        TextView titleText = findViewById(R.id.title);
        TextView msgText = findViewById(R.id.content);
        Button leftBtn = findViewById(R.id.left_button);
        Button rightBtn = findViewById(R.id.right_button);
        View dividerView = findViewById(R.id.divider_view);
        if (TextUtils.isEmpty(mTitle)) {
            titleText.setVisibility(View.GONE);
        } else {
            titleText.setText(mTitle);
            titleText.setTextSize(titleSize);
            titleText.setVisibility(View.VISIBLE);
            titleText.setTextColor(mTitleColor);
        }
        if (!TextUtils.isEmpty(mMessage)) {
            msgText.setText(mMessage);
            msgText.setGravity(gravity);
            msgText.setTextSize(mMsgTextSize);
            if(movementMethod!=null){
                msgText.setMovementMethod(movementMethod);
            }
        }
        if (isShowDivider) {
            dividerView.setVisibility(View.VISIBLE);
        } else {
            dividerView.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mRightText)) {
            rightBtn.setText(mRightText);
            rightBtn.setVisibility(View.VISIBLE);
            if (mRightTextColor != 0) {
                rightBtn.setTextColor(mRightTextColor);
            }
            rightBtn.setOnClickListener(v -> {
                cancel();
                if (null != mOnRightListener) {
                    mOnRightListener.onCallback();
                }
            });
        } else {
            rightBtn.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mLeftText)) {
            leftBtn.setText(mLeftText);
            leftBtn.setVisibility(View.VISIBLE);
            if (mLeftTextColor != 0) {
                leftBtn.setTextColor(mLeftTextColor);
            }
            leftBtn.setOnClickListener(v -> {
                cancel();
                if (null != mOnLeftListener) {
                    mOnLeftListener.onCallback();
                }
            });
        } else {
            leftBtn.setVisibility(View.GONE);
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

    public void setRightBtn(@StringRes int rightStringRes, OnSimpleListener listener) {
        setRightBtn(getString(rightStringRes), listener);
    }

    public void setLeftBtn(@StringRes int leftStringRes, OnSimpleListener listener) {
        setLeftBtn(getString(leftStringRes), listener);
    }

    public void setRightBtn(CharSequence rightText, OnSimpleListener listener) {
        this.mRightText = rightText;
        this.mOnRightListener = listener;
    }

    public void setLeftBtn(CharSequence leftText, OnSimpleListener listener) {
        this.mLeftText = leftText;
        this.mOnLeftListener = listener;
        this.isShowDivider = true;
    }

    public void setRightBtn(CharSequence rightText) {
        this.mRightText = rightText;
    }

    public void setLeftBtn(CharSequence leftText) {
        this.mLeftText = leftText;
        this.isShowDivider = true;
    }
}