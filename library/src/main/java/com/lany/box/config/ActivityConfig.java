package com.lany.box.config;

import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;

import lombok.Getter;

@Getter
public class ActivityConfig {
    /**
     * 布局文件id
     */
    @LayoutRes
    private int layoutId;
    /**
     * Toolbar布局文件id
     */
    @LayoutRes
    private int toolBarLayoutId;
    /**
     * 状态栏的文字和图标是否改成黑色
     */
    private boolean statusBarDarkFont;
    /**
     * 是否需要Toolbar
     */
    private boolean hasToolbar;
    /**
     * Toolbar是否需要显示返回键
     */
    private boolean hasBackBtn;
    /**
     * toolbar的高度
     */
    private int toolbarHeight;
    /**
     * Toolbar颜色
     */
    @ColorRes
    private int toolbarColor;
    /**
     * 是否处理软键盘
     */
    private boolean keyboardEnable;
    /**
     * 状态栏颜色
     */
    @ColorRes
    private int statusBarColor;
    /**
     * 状态栏是否透明
     */
    private boolean transparentStatusBar;
    /**
     * 是否全屏
     */
    private boolean fullscreen;

    /**
     * toolbar标题
     */
    private CharSequence title;
    /**
     * 内容的背景颜色
     */
    @ColorRes
    private int contentColor;

    public ActivityConfig layoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public ActivityConfig toolBarLayoutId(int toolBarLayoutId) {
        this.toolBarLayoutId = toolBarLayoutId;
        return this;
    }

    public ActivityConfig statusBarDarkFont(boolean statusBarDarkFont) {
        this.statusBarDarkFont = statusBarDarkFont;
        return this;
    }

    public ActivityConfig hasToolbar(boolean hasToolbar) {
        this.hasToolbar = hasToolbar;
        return this;
    }

    public ActivityConfig hasBackBtn(boolean hasBackBtn) {
        this.hasBackBtn = hasBackBtn;
        return this;
    }

    public ActivityConfig toolbarHeight(int toolbarHeight) {
        this.toolbarHeight = toolbarHeight;
        return this;
    }

    public ActivityConfig toolbarColor(@ColorRes int color) {
        this.toolbarColor = color;
        return this;
    }

    public ActivityConfig keyboardEnable(boolean keyboardEnable) {
        this.keyboardEnable = keyboardEnable;
        return this;
    }

    public ActivityConfig statusBarColor(@ColorRes int color) {
        this.statusBarColor = color;
        return this;
    }

    public ActivityConfig transparentStatusBar(boolean transparentStatusBar) {
        this.transparentStatusBar = transparentStatusBar;
        return this;
    }

    public ActivityConfig fullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
        return this;
    }

    public ActivityConfig title(CharSequence title) {
        this.title = title;
        return this;
    }

    public ActivityConfig contentColor(@ColorRes int color) {
        this.contentColor = color;
        return this;
    }
}
