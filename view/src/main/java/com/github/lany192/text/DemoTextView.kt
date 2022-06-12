package com.github.lany192.text

import android.content.Context
import android.graphics.Color
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import kotlin.math.ceil

/**
 * 限制最大行数且在最后显示...全文
 */
class DemoTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BoxTextView(context, attrs, defStyleAttr) {
    private var mBufferType = BufferType.NORMAL

    /**
     * 尾部更多文字
     */
    private val moreText: String = "...全文"

    /**
     * 是否已经展开
     */
    private var expand = false

    private var expandable = false

    private var mOrigText: CharSequence? = null

    /**
     * 是否可以展开
     */
    fun setExpandable(expandable: Boolean) {
        this.expand = false
        this.expandable = expandable
        if (expandable) {
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }
    }

    override fun setText(text: CharSequence, type: BufferType) {
        var content = text
        if (TextUtils.isEmpty(content)) {
            content = ""
        }
        if (TextUtils.isEmpty(mOrigText)) {
            mOrigText = content
        }
        mBufferType = type
        super.setText(content, type)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (lineCount > maxLines) {
            //如果大于设置的最大行数
            val (stringBuilder, sb) = clipContent()
            stringBuilder.append(sb)
            //setMeasuredDimension(measuredWidth, getDesiredHeight(layout))
            text = stringBuilder
        }
    }

    /**
     * 裁剪内容
     */
    private fun clipContent(): Pair<SpannableStringBuilder, SpannableString> {
        var offset = 1
        val staticLayout = StaticLayout(
            text,
            layout.paint,
            layout.width,
            Layout.Alignment.ALIGN_NORMAL,
            layout.spacingMultiplier,
            layout.spacingAdd,
            false
        )
        val indexEnd = staticLayout.getLineEnd(maxLines - 1)
        val tempText = text.subSequence(0, indexEnd)
        var offsetWidth = layout.paint.measureText(tempText[indexEnd - 1].toString()).toInt()
        val moreWidth = ceil(layout.paint.measureText(moreText).toDouble()).toInt()
        //表情字节个数
        var countEmoji = 0
        while (indexEnd > offset && offsetWidth <= moreWidth) {
            //当前字节是否位表情
            val isEmoji = isEmojiCharacter(tempText[indexEnd - offset])
            if (isEmoji) {
                countEmoji += 1
            }
            offset++
            val pair = getOffsetWidth(
                indexEnd,
                offset,
                tempText,
                countEmoji, offsetWidth, layout, moreWidth
            )
            offset = pair.first
            offsetWidth = pair.second
        }
        val ssbShrink = tempText.subSequence(0, indexEnd - offset)
        val stringBuilder = SpannableStringBuilder(ssbShrink)


        val sb = SpannableString(moreText)
        sb.setSpan(ForegroundColorSpan(Color.BLUE), 3, sb.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        if (expandable) {
            sb.setSpan(object : ClickableSpan() {
                override fun updateDrawState(paint: TextPaint) {
                    paint.color = Color.GREEN
                    paint.isUnderlineText = false
                }

                override fun onClick(widget: View) {
                    expand = true
                    maxLines = Int.MAX_VALUE
                    setText(mOrigText)
                    requestLayout()

                    Toast.makeText(context, "1展开全文", Toast.LENGTH_SHORT).show()
                }
            }, 3, sb.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        }
        return Pair(stringBuilder, sb)
    }

    private fun getOffsetWidth(
        indexEnd: Int,
        offset: Int,
        tempText: CharSequence,
        countEmoji: Int,
        offsetWidth: Int,
        layout: Layout,
        moreWidth: Int
    ): Pair<Int, Int> {
        var offset1 = offset
        var offsetWidth1 = offsetWidth
        if (indexEnd > offset1) {
            val text = tempText[indexEnd - offset1 - 1].toString().trim()
            if (text.isNotEmpty() && countEmoji % 2 == 0) {
                val charText = tempText[indexEnd - offset1]
                offsetWidth1 += layout.paint.measureText(charText.toString()).toInt()
                //一个表情两个字符，避免截取一半字符出现乱码或者显示不全...全文
                if (offsetWidth1 > moreWidth && isEmojiCharacter(charText)) {
                    offset1++
                }
            }
        } else {
            val charText = tempText[indexEnd - offset1]
            offsetWidth1 += layout.paint.measureText(charText.toString()).toInt()
        }
        return Pair(offset1, offsetWidth1)
    }

    /**
     * 获取内容高度
     */
    private fun getDesiredHeight(layout: Layout?): Int {
        if (layout == null) {
            return 0
        }
        val lineTop: Int
        val lineCount = layout.lineCount
        val compoundPaddingTop =
            compoundPaddingTop + compoundPaddingBottom - lineSpacingExtra.toInt()
        lineTop = when {
            lineCount > maxLines -> {
                //文字行数超过最大行
                layout.getLineTop(maxLines)
            }
            else -> {
                layout.getLineTop(lineCount)
            }
        }
        return (lineTop + compoundPaddingTop).coerceAtLeast(suggestedMinimumHeight)
    }

    private fun isEmojiCharacter(codePoint: Char): Boolean {
        return !(codePoint.toInt() == 0x0 || codePoint.toInt() == 0x9 || codePoint.toInt() == 0xA || codePoint.toInt() == 0xD || codePoint.toInt() in 0x20..0xD7FF || codePoint.toInt() in 0xE000..0xFFFD || codePoint.toInt() in 0x10000..0x10FFFF)
    }
}