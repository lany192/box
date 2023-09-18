package com.github.lany192.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ViewAnimator;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.List;

/**
 * 跑马灯视图
 */
public abstract class MarqueeLayout extends ViewAnimator
        implements DefaultLifecycleObserver {
    /**
     * 轮换时间间隔
     */
    private long duration = 2000L;
    private int size;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean started;

    public MarqueeLayout(Context context) {
        this(context, null);
    }

    public MarqueeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().addObserver(this);
        }
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        stopAnimation();
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        startAnimation();
    }

    public <V extends View> void setViews(List<V> views, long duration) {
        if (views == null || views.size() == 0) {
            return;
        }
        if (duration >= 100) {
            this.duration = duration;
        }
        size = views.size();
        removeAllViews();
        for (V view : views) {
            addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }
        startAnimation();
    }

    public void startAnimation() {
        stopAnimation();
        if (!started && size > 1) {
            started = true;
            setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.vertical_marquee_in));
            setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.vertical_marquee_out));
            handler.postDelayed(new Runnable() {
                public void run() {
                    showNext();
                    handler.postDelayed(this, duration);
                }
            }, duration);
        }
    }

    public void stopAnimation() {
        started = false;
        handler.removeCallbacksAndMessages(null);
        if (getInAnimation() != null) {
            getInAnimation().cancel();
        }
        if (getOutAnimation() != null) {
            getOutAnimation().cancel();
        }
    }
}
