package com.github.lany192.box.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.github.lany192.box.R;

public class FooterView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.view_footer;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.view_footer_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.view_footer_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.view_footer_end_view;
    }
}
