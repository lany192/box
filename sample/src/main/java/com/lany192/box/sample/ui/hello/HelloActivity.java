package com.lany192.box.sample.ui.hello;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.items.PageActivity;
import com.github.lany192.utils.DensityUtils;
import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.lany192.box.sample.data.binder.ArticleBinder;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/hello")
public class HelloActivity extends PageActivity<HelloViewModel> {
    @Inject
    ArticleBinder articleBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        register(articleBinder);
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration(RecyclerView.Adapter<?> adapter) {
        if (getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
            MaterialDividerItemDecoration decoration = new MaterialDividerItemDecoration(this, layoutManager.getOrientation());
            decoration.setDividerColor(Color.RED);
            decoration.setLastItemDecorated(false);
            decoration.setDividerInsetEnd(DensityUtils.dp2px(16));
            decoration.setDividerInsetStart(DensityUtils.dp2px(16));
            return decoration;
        }
        return super.getItemDecoration(adapter);
    }
}