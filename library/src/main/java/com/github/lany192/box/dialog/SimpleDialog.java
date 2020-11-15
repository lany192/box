package com.github.lany192.box.dialog;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.StringRes;

import com.elvishew.xlog.XLog;
import com.github.lany192.box.R;
import com.klinker.android.link_builder.TouchableMovementMethod;

//    SimpleDialog dialog = new SimpleDialog();
//    dialog.setTitle("提示");
//    dialog.setMessage("猜猜我是谁");
//    dialog.setCancelable(true);
//    dialog.setCanceledOnTouchOutside(true);
//    dialog.setRightBtn("确定", new SimpleDialog.OnRightListener() {
//        @Override
//        public void onClicked() {
//
//        }
//    });
//    dialog.setLeftBtn("取消", new SimpleDialog.OnLeftListener() {
//        @Override
//        public void onClicked() {
//
//        }
//    });
//    dialog.show(this);

public class SimpleDialog extends DialogFragment {
    private OnRightListener mOnRightListener;
    private OnLeftListener mOnLeftListener;
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

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_simple;
    }

    @Override
    protected void init() {
        TextView titleText = findViewById(R.id.dialog_simple_title);
        TextView msgText = findViewById(R.id.dialog_simple_content);
        Button leftBtn = findViewById(R.id.dialog_simple_left_btn);
        Button rightBtn = findViewById(R.id.dialog_simple_right_btn);
        View dividerView = findViewById(R.id.dialog_simple_divider_view);
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
            msgText.setMovementMethod(TouchableMovementMethod.getInstance());//为了预防能后点击
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
                    mOnRightListener.onClicked();
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
                    mOnLeftListener.onClicked();
                }
            });
        } else {
            leftBtn.setVisibility(View.GONE);
        }
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
    }

    public void setTitleColor(@ColorInt int colorIntId) {
        this.mTitleColor = colorIntId;
    }

    public void setTitleSize(float titleSize) {
        if (titleSize < 8) {//字体最小不能小于8sp
            XLog.tag(TAG).w(TAG + "  字体太小，请设置合理的字体大小");
        }
        this.titleSize = titleSize;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void setMessage(CharSequence message) {
        this.mMessage = message;
    }

    public void setMessageTextSize(float size) {
        if (mMsgTextSize < 8) {//字体最小不能小于8sp
            XLog.tag(TAG).w(TAG + "  字体太小，请设置合理的字体大小");
        }
        this.mMsgTextSize = size;
    }

    public void setRightTextColor(@ColorInt int colorIntId) {
        this.mRightTextColor = colorIntId;
    }

    public void setLeftTextColor(@ColorInt int leftTextColor) {
        this.mLeftTextColor = leftTextColor;
    }

    public void setRightBtn(@StringRes int rightStringRes, OnRightListener listener) {
        setRightBtn(getString(rightStringRes), listener);
    }

    public void setLeftBtn(@StringRes int leftStringRes, OnLeftListener listener) {
        setLeftBtn(getString(leftStringRes), listener);
    }

    public void setRightBtn(CharSequence rightText, OnRightListener listener) {
        this.mRightText = rightText;
        this.mOnRightListener = listener;
    }

    public void setLeftBtn(CharSequence leftText, OnLeftListener listener) {
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

    public interface OnRightListener {
        void onClicked();
    }

    public interface OnLeftListener {
        void onClicked();
    }
}