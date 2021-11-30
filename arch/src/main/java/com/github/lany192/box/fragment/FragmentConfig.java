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

    public int getToolBarLayoutId() {
        return toolBarLayoutId;
    }

    public int getToolbarHeight() {
        return toolbarHeight;
    }

    public int getToolbarColor() {
        return toolbarColor;
    }

    public boolean isCoverStyle() {
        return coverStyle;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int layoutId;
        private int toolBarLayoutId;
        private int toolbarHeight;
        private int toolbarColor;
        private boolean coverStyle;

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
            FragmentConfig fragmentConfig = new FragmentConfig();
            fragmentConfig.layoutId = this.layoutId;
            fragmentConfig.coverStyle = this.coverStyle;
            fragmentConfig.toolBarLayoutId = this.toolBarLayoutId;
            fragmentConfig.toolbarHeight = this.toolbarHeight;
            fragmentConfig.toolbarColor = this.toolbarColor;
            return fragmentConfig;
        }
    }
}
