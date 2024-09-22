package com.lany192.box.demo.ui.guide

import android.widget.FrameLayout
import com.github.lany192.arch.adapter.BindingAdapter
import com.lany192.box.demo.databinding.ItemGuideBinding
import org.libpag.PAGImageView

class GuideAdapter(data: MutableList<String>, val height: Int, val listener: OnGuideListener) :
    BindingAdapter<String, ItemGuideBinding>(data) {
    private var currentPosition = 0

    override fun convert(binding: ItemGuideBinding, item: String, position: Int) {
        val imageView = PAGImageView(context)
        imageView.setRepeatCount(1)
        imageView.setPath(item)
        imageView.addListener(object : OnPagAnimationListener() {

            override fun onAnimationEnd(pagImageView: PAGImageView) {
                listener.onAnimationEnd(position)
            }
        })
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height)
        binding.container.removeAllViews()
        binding.container.addView(imageView, layoutParams)
        if (currentPosition == position) {
            imageView.play()
        }
    }

    fun setCurrentPosition(position: Int) {
        notifyItemChanged(currentPosition)
        currentPosition = position
        notifyItemChanged(currentPosition)
    }
}

interface OnGuideListener {
    fun onAnimationEnd(position: Int) {}
}
