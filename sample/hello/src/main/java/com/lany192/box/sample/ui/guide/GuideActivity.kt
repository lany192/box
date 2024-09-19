package com.lany192.box.sample.ui.guide

import android.os.Bundle
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewBindingActivity
import com.github.lany192.extension.addStatusBarPadding
import com.github.lany192.extension.gone
import com.github.lany192.extension.visible
import com.github.lany192.utils.PhoneUtils
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.lany192.box.sample.databinding.ActivityGuideBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/guide")
class GuideActivity : ViewBindingActivity<ActivityGuideBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this)
            .titleBar(binding.toolbar)
            .hideBar(BarHide.FLAG_HIDE_BAR)
            .init()
        binding.toolbar.addStatusBarPadding()
        binding.skip.setOnClickListener { finish() }
        binding.start.setOnClickListener { finish() }
        val items = mutableListOf<String>()
        items.add("assets://guide/guide_1.pag")
        items.add("assets://guide/guide_2.pag")
        items.add("assets://guide/guide_3.pag")
        items.add("assets://guide/guide_4.pag")
        val imageViewHeight = PhoneUtils.getDeviceWidth() * 1.8222f
        val adapter = GuideAdapter(items, imageViewHeight.toInt(), object : OnGuideListener {

            override fun onAnimationEnd(position: Int) {
                if (position < items.size - 1) {
                    binding.viewPager.currentItem += 1
                }
            }
        })
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == items.size - 1) {
                    binding.skip.gone()
                    binding.start.visible()
                    binding.indicator.gone()
                } else {
                    binding.skip.visible()
                    binding.start.gone()
                    binding.indicator.visible()
                }
                adapter.setCurrentPosition(position)
            }
        })
        binding.indicator.setWithViewPager2(binding.viewPager)

        var freeSpaceHeight = PhoneUtils.getDeviceHeight() -
                imageViewHeight - binding.bottom.layoutParams.height
        if (freeSpaceHeight < 0) {
            freeSpaceHeight = 0.0f
        }
        (binding.viewPager.layoutParams as ViewGroup.MarginLayoutParams).apply {
            topMargin = (freeSpaceHeight / 2.0f).toInt()
        }
        (binding.bottom.layoutParams as ViewGroup.MarginLayoutParams).apply {
            topMargin = (imageViewHeight + freeSpaceHeight / 2.0f).toInt()
        }
    }
}
