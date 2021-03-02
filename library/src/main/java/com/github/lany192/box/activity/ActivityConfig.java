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

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getToolBarLayoutId() {
        return toolBarLayoutId;
    }

    public void setToolBarLayoutId(int toolBarLayoutId) {
        this.toolBarLayoutId = toolBarLayoutId;
    }

    public boolean isStatusBarDarkFont() {
        return statusBarDarkFont;
    }

    public void setStatusBarDarkFont(boolean statusBarDarkFont) {
        this.statusBarDarkFont = statusBarDarkFont;
    }

    public boolean isHasBackBtn() {
        return hasBackBtn;
    }

    public void setHasBackBtn(boolean hasBackBtn) {
        this.hasBackBtn = hasBackBtn;
    }

    public int getToolbarHeight() {
        return toolbarHeight;
    }

    public void setToolbarHeight(int toolbarHeight) {
        this.toolbarHeight = toolbarHeight;
    }

    public boolean isKeyboardEnable() {
        return keyboardEnable;
    }

    public void setKeyboardEnable(boolean keyboardEnable) {
        this.keyboardEnable = keyboardEnable;
    }

    public int getStatusBarColor() {
        return statusBarColor;
    }

    public void setStatusBarColor(int statusBarColor) {
        this.statusBarColor = statusBarColor;
    }

    public boolean isTransparentStatusBar() {
        return transparentStatusBar;
    }

    public void setTransparentStatusBar(boolean transparentStatusBar) {
        this.transparentStatusBar = transparentStatusBar;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public static Builder builder() {
        return Builder.builder();
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

        public static Builder builder() {
            return new Builder();
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
            ActivityConfig hello = new ActivityConfig();
            hello.setLayoutId(layoutId);
            hello.setToolBarLayoutId(toolBarLayoutId);
            hello.setStatusBarDarkFont(statusBarDarkFont);
            hello.setHasBackBtn(hasBackBtn);
            hello.setToolbarHeight(toolbarHeight);
            hello.setKeyboardEnable(keyboardEnable);
            hello.setStatusBarColor(statusBarColor);
            hello.setTransparentStatusBar(transparentStatusBar);
            hello.setFullscreen(fullscreen);
            hello.setTitle(title);
            return hello;
        }
    }
}
