package com.github.lany192.box.fragment;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;

import com.github.lany192.box.R;
import com.github.lany192.box.utils.DensityUtils;

import lombok.Getter;

@Getter
public class FragmentConfig {
    /**
     * 布局文件id
     */
    @LayoutRes
    private int layoutId;
    /**
     * Toolbar布局文件id
     */
    @LayoutRes
    private int toolBarLayoutId = R.layout.ui_default;
    /**
     * 是否需要Toolbar
     */
    private boolean hasToolbar;
    /**
     * toolbar的高度
     */
    private int toolbarHeight = DensityUtils.dp2px(48);
    /**
     * Toolbar颜色
     */
    @ColorRes
    private int toolbarColor;

    /**
     * 是否需要Toolbar模糊化
     */
    private boolean toolbarBlur;
    /**
     * 是否toolbar覆盖方式
     */
    private boolean coverStyle;

    public FragmentConfig coverStyle(boolean cover) {
        this.coverStyle = cover;
        return this;
    }

    public FragmentConfig toolbarBlur(boolean blur) {
        this.toolbarBlur = blur;
        return this;
    }

    public FragmentConfig layoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public FragmentConfig toolBarLayoutId(int toolBarLayoutId) {
        this.toolBarLayoutId = toolBarLayoutId;
        this.hasToolbar = true;
        return this;
    }

    public FragmentConfig toolbarHeight(int toolbarHeight) {
        this.toolbarHeight = toolbarHeight;
        return this;
    }

    public FragmentConfig toolbarColor(int color) {
        this.toolbarColor = color;
        return this;
    }
}
