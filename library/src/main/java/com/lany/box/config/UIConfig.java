package com.lany.box.config;

import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;

import lombok.Getter;

@Getter
public class UIConfig {
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

    public UIConfig layoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public UIConfig toolBarLayoutId(int toolBarLayoutId) {
        this.toolBarLayoutId = toolBarLayoutId;
        return this;
    }

    public UIConfig statusBarDarkFont(boolean statusBarDarkFont) {
        this.statusBarDarkFont = statusBarDarkFont;
        return this;
    }

    public UIConfig hasToolbar(boolean hasToolbar) {
        this.hasToolbar = hasToolbar;
        return this;
    }

    public UIConfig hasBackBtn(boolean hasBackBtn) {
        this.hasBackBtn = hasBackBtn;
        return this;
    }

    public UIConfig toolbarHeight(int toolbarHeight) {
        this.toolbarHeight = toolbarHeight;
        return this;
    }

    public UIConfig toolbarColor(int toolbarColor) {
        this.toolbarColor = toolbarColor;
        return this;
    }

    public UIConfig keyboardEnable(boolean keyboardEnable) {
        this.keyboardEnable = keyboardEnable;
        return this;
    }

    public UIConfig statusBarColor(int statusBarColor) {
        this.statusBarColor = statusBarColor;
        return this;
    }

    public UIConfig transparentStatusBar(boolean transparentStatusBar) {
        this.transparentStatusBar = transparentStatusBar;
        return this;
    }

    public UIConfig fullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
        return this;
    }

    public UIConfig title(CharSequence title) {
        this.title = title;
        return this;
    }
}
