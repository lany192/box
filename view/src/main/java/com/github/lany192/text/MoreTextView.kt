package com.github.lany192.text

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
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
class MoreTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BoxTextView(context, attrs, defStyleAttr) {

    init {
        movementMethod = LinkMovementMethod.getInstance()
        highlightColor = Color.TRANSPARENT
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (lineCount > maxLines) {
            text = clipText()
        }
    }

    private fun clipText(): SpannableStringBuilder {
        val moreText = "...全文"
        val indexEnd = layout.getLineEnd(maxLines - 1)
        val tempText = text.subSequence(0, indexEnd)

        val moreWidth = ceil(layout.paint.measureText(moreText).toDouble()).toInt()

        var offsetWidth = layout.paint.measureText(tempText[indexEnd - 1].toString()).toInt()
        //表情字节个数
        var countEmoji = 0
        var offset = 1
        while (indexEnd > offset && offsetWidth <= moreWidth) {
            //当前字节是否位表情
            val isEmoji = isEmoji(tempText[indexEnd - offset])
            if (isEmoji) {
                countEmoji += 1
            }
            offset++

            if (indexEnd > offset) {
                val text = tempText[indexEnd - offset - 1].toString().trim()
                if (text.isNotEmpty() && countEmoji % 2 == 0) {
                    val charText = tempText[indexEnd - offset]
                    offsetWidth += layout.paint.measureText(charText.toString()).toInt()
                    //一个表情两个字符，避免截取一半字符出现乱码或者显示不全...全文
                    if (offsetWidth > moreWidth && isEmoji(charText)) {
                        offset++
                    }
                }
            } else {
                val charText = tempText[indexEnd - offset]
                offsetWidth += layout.paint.measureText(charText.toString()).toInt()
            }
        }
        val ssbShrink = tempText.subSequence(0, indexEnd - offset)
        val stringBuilder = SpannableStringBuilder(ssbShrink)


        val sb = SpannableString(moreText)
        sb.setSpan(ForegroundColorSpan(Color.BLUE), 3, sb.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        sb.setSpan(object : ClickableSpan() {
            override fun updateDrawState(paint: TextPaint) {
                paint.color = Color.GREEN
                paint.isUnderlineText = false
            }

            override fun onClick(widget: View) {
                Toast.makeText(context, "点击了全文", Toast.LENGTH_SHORT).show()
            }
        }, 3, sb.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        stringBuilder.append(sb)
        return stringBuilder
    }

    private fun isEmoji(codePoint: Char): Boolean {
        return !(codePoint.toInt() == 0x0 || codePoint.toInt() == 0x9 || codePoint.toInt() == 0xA || codePoint.toInt() == 0xD || codePoint.toInt() in 0x20..0xD7FF || codePoint.toInt() in 0xE000..0xFFFD || codePoint.toInt() in 0x10000..0x10FFFF)
    }
}