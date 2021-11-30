package com.github.lany192.box.activity;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;

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
     * Toolbar是否需要显示返回键
     */
    private boolean hasBackBtn;
    /**
     * toolbar的高度
     */
    private int toolbarHeight;
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
     * 是否需要Toolbar
     */
    public boolean hasToolbar() {
        return toolBarLayoutId != 0;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public int getToolBarLayoutId() {
        return toolBarLayoutId;
    }

    public boolean isStatusBarDarkFont() {
        return statusBarDarkFont;
    }

    public boolean isHasBackBtn() {
        return hasBackBtn;
    }

    public int getToolbarHeight() {
        return toolbarHeight;
    }

    public boolean isKeyboardEnable() {
        return keyboardEnable;
    }

    public int getStatusBarColor() {
        return statusBarColor;
    }

    public boolean isTransparentStatusBar() {
        return transparentStatusBar;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public CharSequence getTitle() {
        return title;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int layoutId;
        private int toolBarLayoutId;
        private boolean statusBarDarkFont;
        private boolean hasBackBtn;
        private int toolbarHeight;
        private boolean keyboardEnable;
        private int statusBarColor;
        private boolean transparentStatusBar;
        private boolean fullscreen;
        private CharSequence title;

        private Builder() {
        }

        public Builder layoutId(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        public Builder toolBarLayoutId(int toolBarLayoutId) {
            this.toolBarLayoutId = toolBarLayoutId;
            return this;
        }

        public Builder statusBarDarkFont(boolean statusBarDarkFont) {
            this.statusBarDarkFont = statusBarDarkFont;
            return this;
        }

        public Builder hasBackBtn(boolean hasBackBtn) {
            this.hasBackBtn = hasBackBtn;
            return this;
        }

        public Builder toolbarHeight(int toolbarHeight) {
            this.toolbarHeight = toolbarHeight;
            return this;
        }

        public Builder keyboardEnable(boolean keyboardEnable) {
            this.keyboardEnable = keyboardEnable;
            return this;
        }

        public Builder statusBarColor(int statusBarColor) {
            this.statusBarColor = statusBarColor;
            return this;
        }

        public Builder transparentStatusBar(boolean transparentStatusBar) {
            this.transparentStatusBar = transparentStatusBar;
            return this;
        }

        public Builder fullscreen(boolean fullscreen) {
            this.fullscreen = fullscreen;
            return this;
        }

        public Builder title(CharSequence title) {
            this.title = title;
            return this;
        }

        public ActivityConfig build() {
            ActivityConfig activityConfig = new ActivityConfig();
            activityConfig.statusBarDarkFont = this.statusBarDarkFont;
            activityConfig.layoutId = this.layoutId;
            activityConfig.toolBarLayoutId = this.toolBarLayoutId;
            activityConfig.statusBarColor = this.statusBarColor;
            activityConfig.transparentStatusBar = this.transparentStatusBar;
            activityConfig.fullscreen = this.fullscreen;
            activityConfig.title = this.title;
            activityConfig.hasBackBtn = this.hasBackBtn;
            activityConfig.toolbarHeight = this.toolbarHeight;
            activityConfig.keyboardEnable = this.keyboardEnable;
            return activityConfig;
        }
    }
}
