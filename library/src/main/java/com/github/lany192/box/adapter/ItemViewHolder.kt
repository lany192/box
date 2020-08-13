package com.github.lany192.box.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import butterknife.ButterKnife
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.lany192.box.helper.ImageLoader

class ItemViewHolder(itemView: View?) : BaseViewHolder(itemView!!) {

    init {
        ButterKnife.bind(this, itemView!!)
    }

    override fun setText(@IdRes viewId: Int, value: CharSequence?): BaseViewHolder {
        return super.setText(viewId, if (TextUtils.isEmpty(value)) "" else value)
    }

    override fun setText(@IdRes viewId: Int, value: Int): BaseViewHolder? {
        return setText(viewId, value.toString())
    }

    override fun setTextColor(@IdRes viewId: Int, @ColorInt color: Int): BaseViewHolder {
        val view = getView<TextView>(viewId)
        view.setTextColor(color)
        return this
    }

    fun setImageUrl(@IdRes viewId: Int, picUrl: String?): ItemViewHolder {
        val imageView = getView<ImageView>(viewId)
        ImageLoader.get().show(imageView, picUrl)
        return this
    }

    fun setImageFullWidth(@IdRes viewId: Int, picUrl: String?): ItemViewHolder {
        val imageView = getView<ImageView>(viewId)
        ImageLoader.get().showFullWidth(imageView, picUrl)
        return this
    }

    fun setAvatarUrl(@IdRes viewId: Int, picUrl: String?): ItemViewHolder {
        val imageView = getView<ImageView>(viewId)
        imageView.layoutParams.height = imageView.layoutParams.width
        ImageLoader.get().avatar(imageView, picUrl)
        return this
    }

    fun setTextSize(@IdRes viewId: Int, size: Float): ItemViewHolder {
        val view = getView<TextView>(viewId)
        view.textSize = size
        return this
    }

    fun setVisibility(@IdRes viewId: Int, visibility: Int): ItemViewHolder {
        if (visibility == View.GONE || visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            getView<View>(viewId).visibility = visibility
        } else {
            throw RuntimeException("Parameter visibility is an illegal value！")
        }
        return this
    }
}