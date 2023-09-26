package com.github.lany192.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.lany192.dialog.databinding.DialogImageBinding;
import com.github.lany192.interfaces.OnSimpleListener;
import com.github.lany192.utils.ContextUtils;

/**
 * 图片弹窗
 */
public class ImageDialog extends BaseDialog<DialogImageBinding> {
    private Object model;
    private OnSimpleListener mListener;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void init() {
        if (!ContextUtils.check(getContext())) {
            cancel();
            return;
        }
        Glide.with(this)
                .load(model)
                .apply(new RequestOptions()
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .format(DecodeFormat.PREFER_ARGB_8888)
                )
                .into(binding.image);
        binding.bottom.setOnTouchListener((v, event) -> {
            cancel();
            return false;
        });
        binding.close.setOnClickListener(v -> cancel());
        binding.image.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onCallback();
            }
            cancel();
        });
    }

    public void setImage(Object model) {
        this.model = model;
    }

    public void setOnClickListener(OnSimpleListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 先加载后显示
     */
    public void showWhenLoaded(Context context) {
        if (model == null) {
            return;
        }
        Glide.with(ContextUtils.getContext())
                .load(model)
                .apply(new RequestOptions()
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .format(DecodeFormat.PREFER_ARGB_8888))
                .addListener(new RequestListener<>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ImageDialog.super.show(context);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        ImageDialog.super.show(context);
                        return false;
                    }
                })
                .preload();
    }
}
