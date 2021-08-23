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
import com.github.lany192.box.databinding.DialogSimpleBinding;
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

public class SimpleDialog extends DialogFragment<DialogSimpleBinding> {
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
            binding.content.setMovementMethod(TouchableMovementMethod.getInstance());//为了预防能后点击
        }
        if (isShowDivider) {
            binding.dividerView.setVisibility(View.VISIBLE);
        } else {
            binding.dividerView.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mRightText)) {
            binding.rightBtn.setText(mRightText);
            binding.rightBtn.setVisibility(View.VISIBLE);
            if (mRightTextColor != 0) {
                binding.rightBtn.setTextColor(mRightTextColor);
            }
            binding.rightBtn.setOnClickListener(v -> {
                cancel();
                if (null != mOnRightListener) {
                    mOnRightListener.onClicked();
                }
            });
        } else {
            binding.rightBtn.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mLeftText)) {
            binding.leftBtn.setText(mLeftText);
            binding.leftBtn.setVisibility(View.VISIBLE);
            if (mLeftTextColor != 0) {
                binding.leftBtn.setTextColor(mLeftTextColor);
            }
            binding.leftBtn.setOnClickListener(v -> {
                cancel();
                if (null != mOnLeftListener) {
                    mOnLeftListener.onClicked();
                }
            });
        } else {
            binding.leftBtn.setVisibility(View.GONE);
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