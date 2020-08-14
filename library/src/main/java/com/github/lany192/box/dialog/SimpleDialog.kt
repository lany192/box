package com.github.lany192.box.dialog

import android.graphics.Color
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.github.lany192.box.R

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
class SimpleDialog : DialogFragment() {
    private var mOnRightListener: OnRightListener? = null
    private var mOnLeftListener: OnLeftListener? = null
    private var mTitle: CharSequence? = null
    private var mMessage: CharSequence? = null
    private var mLeftText: CharSequence? = null
    private var mRightText: CharSequence? = null
    private var isShowDivider = false
    private var gravity = Gravity.CENTER

    @ColorInt
    private var mTitleColor = Color.BLACK
    private var titleSize = 17f
    private var mMsgTextSize = 15f

    @ColorInt
    private var mRightTextColor = 0

    @ColorInt
    private var mLeftTextColor = 0

    override fun getLayoutId(): Int {
        return R.layout.dialog_simple
    }

    override fun init() {
        val titleText = findViewById<TextView>(R.id.dialog_simple_title)
        val msgText = findViewById<TextView>(R.id.dialog_simple_content)
        val leftBtn = findViewById<Button>(R.id.dialog_simple_left_btn)
        val rightBtn = findViewById<Button>(R.id.dialog_simple_right_btn)
        val dividerView = findViewById<View>(R.id.dialog_simple_divider_view)
        if (TextUtils.isEmpty(mTitle)) {
            titleText.visibility = View.GONE
        } else {
            titleText.text = mTitle
            titleText.textSize = titleSize
            titleText.visibility = View.VISIBLE
            titleText.setTextColor(mTitleColor)
        }
        if (!TextUtils.isEmpty(mMessage)) {
            msgText.text = mMessage
            msgText.gravity = gravity
            msgText.textSize = mMsgTextSize
            msgText.movementMethod = LinkMovementMethod.getInstance() //为了预防能后点击
        }
        if (isShowDivider) {
            dividerView.visibility = View.VISIBLE
        } else {
            dividerView.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(mRightText)) {
            rightBtn.text = mRightText
            rightBtn.visibility = View.VISIBLE
            if (mRightTextColor != 0) {
                rightBtn.setTextColor(mRightTextColor)
            }
            rightBtn.setOnClickListener {
                cancel()
                if (null != mOnRightListener) {
                    mOnRightListener!!.onClicked()
                }
            }
        } else {
            rightBtn.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(mLeftText)) {
            leftBtn.text = mLeftText
            leftBtn.visibility = View.VISIBLE
            if (mLeftTextColor != 0) {
                leftBtn.setTextColor(mLeftTextColor)
            }
            leftBtn.setOnClickListener {
                cancel()
                if (null != mOnLeftListener) {
                    mOnLeftListener!!.onClicked()
                }
            }
        } else {
            leftBtn.visibility = View.GONE
        }
    }

    fun setTitle(title: CharSequence?) {
        mTitle = title
    }

    fun setTitleColor(@ColorInt colorIntId: Int) {
        mTitleColor = colorIntId
    }

    fun setTitleSize(titleSize: Float) {
        if (titleSize < 8) { //字体最小不能小于8sp
            log.w("字体太小，请设置合理的字体大小")
        }
        this.titleSize = titleSize
    }

    fun setGravity(gravity: Int) {
        this.gravity = gravity
    }

    fun setMessage(message: CharSequence?) {
        mMessage = message
    }

    fun setMessageTextSize(size: Float) {
        if (mMsgTextSize < 8) { //字体最小不能小于8sp
            log.w("字体太小，请设置合理的字体大小")
        }
        mMsgTextSize = size
    }

    fun setRightTextColor(@ColorInt colorIntId: Int) {
        mRightTextColor = colorIntId
    }

    fun setLeftTextColor(@ColorInt leftTextColor: Int) {
        mLeftTextColor = leftTextColor
    }

    fun setRightBtn(@StringRes rightStringRes: Int, listener: OnRightListener?) {
        setRightBtn(getString(rightStringRes), listener)
    }

    fun setLeftBtn(@StringRes leftStringRes: Int, listener: OnLeftListener?) {
        setLeftBtn(getString(leftStringRes), listener)
    }

    fun setRightBtn(rightText: CharSequence?, listener: OnRightListener?) {
        mRightText = rightText
        mOnRightListener = listener
    }

    fun setLeftBtn(leftText: CharSequence?, listener: OnLeftListener?) {
        mLeftText = leftText
        mOnLeftListener = listener
        isShowDivider = true
    }

    fun setRightBtn(rightText: CharSequence?) {
        mRightText = rightText
    }

    fun setLeftBtn(leftText: CharSequence?) {
        mLeftText = leftText
        isShowDivider = true
    }

    interface OnRightListener {
        fun onClicked()
    }

    interface OnLeftListener {
        fun onClicked()
    }
}