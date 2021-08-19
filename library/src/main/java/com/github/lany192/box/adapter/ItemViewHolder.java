package com.github.lany192.box.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.box.helper.ImageLoader;

public class ItemViewHolder extends BaseViewHolder {

    public ItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public BaseViewHolder setText(@IdRes int viewId, CharSequence value) {
        return super.setText(viewId, TextUtils.isEmpty(value) ? "" : value);
    }

    @Override
    public BaseViewHolder setText(@IdRes int viewId, int value) {
        return setText(viewId, String.valueOf(value));
    }

    public ItemViewHolder setTextSize(@IdRes int viewId, float size) {
        TextView view = getView(viewId);
        view.setTextSize(size);
        return this;
    }

    @Override
    public BaseViewHolder setTextColor(@IdRes int viewId, @ColorInt int color) {
        TextView view = getView(viewId);
        view.setTextColor(color);
        return this;
    }

    public ItemViewHolder setImage(@IdRes int viewId, String picUrl) {
        setImage(viewId, picUrl, false);
        return this;
    }

    public ItemViewHolder setImage(@IdRes int viewId, String picUrl, boolean fullWidth) {
        ImageView imageView = getView(viewId);
        ImageLoader.get().show(imageView, picUrl, fullWidth);
        return this;
    }

    public ItemViewHolder setAvatar(@IdRes int viewId, String picUrl) {
        ImageView imageView = getView(viewId);
        imageView.getLayoutParams().height = imageView.getLayoutParams().width;
        ImageLoader.get().circle(imageView, picUrl);
        return this;
    }

    public ItemViewHolder setVisibility(@IdRes int viewId, int visibility) {
        if (visibility == View.GONE || visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            getView(viewId).setVisibility(visibility);
        } else {
            throw new RuntimeException("Parameter visibility is an illegal value！");
        }
        return this;
    }
}
