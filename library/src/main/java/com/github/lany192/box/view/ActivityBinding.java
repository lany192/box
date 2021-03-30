package com.github.lany192.box.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.github.lany192.box.databinding.ToolbarDefaultBinding;
import com.github.lany192.box.utils.DensityUtils;

/**
 * @author Administrator
 */
public final class ActivityBinding implements ViewBinding {
    private final LayoutInflater layoutInflater;
    private final ViewBinding toolbar;
    private final ViewBinding content;

    public ActivityBinding(LayoutInflater layoutInflater, @NonNull ViewBinding content) {
        this.layoutInflater = layoutInflater;
        this.content = content;
        this.toolbar = ToolbarDefaultBinding.inflate(layoutInflater);
    }

    public ActivityBinding(LayoutInflater layoutInflater, ViewBinding toolbar, @NonNull ViewBinding content) {
        this.layoutInflater = layoutInflater;
        this.toolbar = toolbar;
        this.content = content;
    }

    public <T extends ViewBinding> T getToolbar() {
        return (T) toolbar;
    }

    public <T extends ViewBinding> T getContent() {
        return (T) content;
    }

    @Override
    @NonNull
    public LinearLayout getRoot() {
        LinearLayout contentView = new LinearLayout(layoutInflater.getContext());
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        if (toolbar != null) {
            contentView.addView(toolbar.getRoot(), new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(48)));
        }
        contentView.addView(content.getRoot(), new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return contentView;
    }
}
