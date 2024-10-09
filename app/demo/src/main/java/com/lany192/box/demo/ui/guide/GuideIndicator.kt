package com.lany192.box.demo.ui.guide

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.github.lany192.extension.dp

class GuideIndicator @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val indicatorGap = 6.dp.toInt()
    private var indicatorItemCount = 0
    private val indicatorWidth = 40.dp.toInt()
    private val indicatorHeight = 2.5f.dp.toInt()
    private var currentPosition = 0
    private var nextPosition = 1
    private var scrollOffset = 0f
    private val indicatorAnimation: SlideAnimation?
    private val painter: RectPainter

    init {
        val paint = Paint()
        paint.style = Paint.Style.FILL
        painter = RectPainter(
            paint, Color.parseColor("#F0F0F0"), Color.parseColor("#131715"),
            indicatorWidth, indicatorHeight,
            1.25f.dp, this
        )
        indicatorAnimation = SlideAnimation { coordinateX: Int ->
            painter.setCoordinateX(coordinateX)
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val viewWidth = when (layoutParams.width) {
            ViewGroup.LayoutParams.MATCH_PARENT -> {
                MeasureSpec.getSize(widthMeasureSpec)
            }
            ViewGroup.LayoutParams.WRAP_CONTENT -> {
                measureWidth()
            }
            else -> {
                layoutParams.width
            }
        }
        val viewHeight = when (layoutParams.height) {
            ViewGroup.LayoutParams.MATCH_PARENT -> {
                MeasureSpec.getSize(heightMeasureSpec)
            }
            ViewGroup.LayoutParams.WRAP_CONTENT -> {
                measureHeight()
            }
            else -> {
                layoutParams.height
            }
        }

        val horizontalPadding = paddingLeft + paddingRight
        val verticalPadding = paddingTop + paddingBottom

        val width = viewWidth + horizontalPadding
        val height = viewHeight + verticalPadding

        setMeasuredDimension(
            resolveSize(width, widthMeasureSpec),
            resolveSize(height, heightMeasureSpec)
        )
    }

    var itemCount: Int
        get() = indicatorItemCount
        set(itemCount) {
            this.indicatorItemCount = itemCount
            invalidate()
            requestLayout()
        }

    private fun getActualPosition(position: Int): Int {
        return if (itemCount != 0) position % itemCount else 0
    }

    fun setWithViewPager2(viewPager2: ViewPager2) {
        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                this@GuideIndicator.onPageScrolled(getActualPosition(position), positionOffset)
            }
        })
        val adapter = viewPager2.adapter
        if (adapter == null) {
            itemCount = 0
        } else {
            itemCount = adapter.itemCount
            adapter.registerAdapterDataObserver(object : AdapterDataObserver() {
                override fun onChanged() {
                    val adapter = viewPager2.adapter
                    if (adapter != null) {
                        this@GuideIndicator.itemCount = adapter.itemCount
                    } else {
                        this@GuideIndicator.itemCount = 0
                    }
                }
            })
        }
    }

    private fun onPageScrolled(position: Int, positionOffset: Float) {
        val rightScrollEnded = position > currentPosition
        val leftScrollEnded = position + 1 < currentPosition

        if (rightScrollEnded || leftScrollEnded) {
            currentPosition = position
        }

        val slideToRight = currentPosition == position && positionOffset != 0f

        var targetPosition: Int
        if (slideToRight) {
            scrollOffset = positionOffset
            targetPosition = position + 1
        } else {
            scrollOffset = 1 - positionOffset
            targetPosition = position
        }

        targetPosition = if (itemCount <= 0 && targetPosition < 0) {
            0
        } else {
            getActualPosition(targetPosition)
        }

        nextPosition = targetPosition

        if (scrollOffset > 1) {
            scrollOffset = 1f
        } else if (scrollOffset < 0) {
            scrollOffset = 0f
        }

        if (scrollOffset == 1f) {
            currentPosition = nextPosition
        }

        val start = getCoordinateX(currentPosition)
        val end = getCoordinateX(nextPosition)

        if (indicatorAnimation == null) {
            invalidate()
        } else {
            indicatorAnimation.updateValue(start, end)
            indicatorAnimation.schedule(scrollOffset)
        }
    }

    private fun measureWidth(): Int {
        return indicatorWidth * itemCount + indicatorGap * (itemCount - 1)
    }

    private fun measureHeight(): Int {
        return indicatorHeight
    }

    fun getCoordinateX(position: Int): Int {
        var coordinate = paddingLeft
        for (i in 0 until itemCount) {
            if (i == position) {
                return coordinate
            }
            coordinate += indicatorWidth + indicatorGap
        }
        return coordinate
    }

    val coordinateY: Int
        get() = paddingTop

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until itemCount) {
            painter.draw(canvas, i)
        }
    }
}


fun interface AnimationUpdateListener {
    fun onAnimationUpdate(coordinateX: Int)
}

class SlideAnimation(animationUpdateListener: AnimationUpdateListener) {
    private var animateCoordinateX = 0
    private val valueAnimator = ValueAnimator()

    init {
        valueAnimator.setDuration(DEFAULT_DURATION.toLong())
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener { animation: ValueAnimator ->
            animateCoordinateX = animation.getAnimatedValue(
                PN_ANIMATE_COORDINATE_X
            ) as Int
            animationUpdateListener.onAnimationUpdate(animateCoordinateX)
        }
    }

    fun schedule(offset: Float) {
        valueAnimator.currentPlayTime = (offset * DEFAULT_DURATION).toLong()
    }

    fun updateValue(start: Int, end: Int) {
        valueAnimator.setValues(PropertyValuesHolder.ofInt(PN_ANIMATE_COORDINATE_X, start, end))
    }

    companion object {
        private const val PN_ANIMATE_COORDINATE_X = "animate_coordinate_x"
        const val DEFAULT_DURATION: Int = 3000
    }
}

class RectPainter(
    var paint: Paint,
    var color: Int,
    private val selectedColor: Int,
    var width: Int,
    var height: Int,
    var cornerRadius: Float,
    var view: GuideIndicator
) {
    private var coordinateX = 0

    fun setCoordinateX(animationValue: Int) {
        this.coordinateX = animationValue
    }

    fun draw(canvas: Canvas, position: Int) {
        val coordinateX = view.getCoordinateX(position)
        val coordinateY = view.coordinateY

        paint.color = color
        canvas.drawRoundRect(
            coordinateX.toFloat(), coordinateY.toFloat(), (coordinateX + width).toFloat(),
            (coordinateY + height).toFloat(), cornerRadius, cornerRadius, paint
        )
        paint.color = selectedColor
        canvas.drawRoundRect(
            this.coordinateX.toFloat(), coordinateY.toFloat(),
            (this.coordinateX + width).toFloat(), (coordinateY + height).toFloat(), cornerRadius,
            cornerRadius, paint
        )
    }
}