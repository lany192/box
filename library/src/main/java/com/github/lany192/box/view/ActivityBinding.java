package com.github.lany192.box.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

/**
 * @author Administrator
 */
public final class ActivityBinding implements ViewBinding {
    private final Context context;
    private ViewBinding toolbar;
    private ViewBinding content;

    public ActivityBinding(Context context) {
        this.context = context;
    }

    @Override
    @NonNull
    public RelativeLayout getRoot() {
        RelativeLayout contentView = new RelativeLayout(context);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        return contentView;
    }

    public ViewBinding getToolbar() {
        return toolbar;
    }

    public void setToolbar(ViewBinding toolbar) {
        this.toolbar = toolbar;
    }

    public ViewBinding getContent() {
        return content;
    }

    public void setContent(ViewBinding content) {
        this.content = content;
    }
}
