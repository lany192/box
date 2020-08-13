package com.github.lany192.box.widget

import android.content.Context
import android.util.AttributeSet
import com.makeramen.roundedimageview.RoundedImageView

/**
 * 正方形ImageView
 */
class SquaredRoundedImageView : RoundedImageView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)
    }
}