package com.github.lany192.box.fragment;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;

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
    private int toolBarLayoutId;
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
     * 是否toolbar覆盖方式
     */
    private boolean coverStyle;

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

    public int getToolbarHeight() {
        return toolbarHeight;
    }

    public void setToolbarHeight(int toolbarHeight) {
        this.toolbarHeight = toolbarHeight;
    }

    public int getToolbarColor() {
        return toolbarColor;
    }

    public void setToolbarColor(int toolbarColor) {
        this.toolbarColor = toolbarColor;
    }

    public boolean isCoverStyle() {
        return coverStyle;
    }

    public void setCoverStyle(boolean coverStyle) {
        this.coverStyle = coverStyle;
    }
    public static Builder builder() {
        return Builder.builder();
    }
    public static final class Builder {
        private int layoutId;
        private int toolBarLayoutId;
        private int toolbarHeight;
        private int toolbarColor;
        private boolean coverStyle;

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

        public Builder toolbarHeight(int toolbarHeight) {
            this.toolbarHeight = toolbarHeight;
            return this;
        }

        public Builder toolbarColor(int toolbarColor) {
            this.toolbarColor = toolbarColor;
            return this;
        }

        public Builder coverStyle(boolean coverStyle) {
            this.coverStyle = coverStyle;
            return this;
        }

        public FragmentConfig build() {
            FragmentConfig hello = new FragmentConfig();
            hello.toolbarColor = this.toolbarColor;
            hello.toolbarHeight = this.toolbarHeight;
            hello.toolBarLayoutId = this.toolBarLayoutId;
            hello.layoutId = this.layoutId;
            hello.coverStyle = this.coverStyle;
            return hello;
        }
    }
}
