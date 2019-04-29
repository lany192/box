package com.lany.box.config;

import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UIConfig {
    /**
     * 返回Toolbar布局文件id
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
    private int toolBarHeight;
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
}
