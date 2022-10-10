package com.lany192.box.sample.ui.goods;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.databinding.ActivityGoodsBinding;
import com.github.lany192.arch.databinding.ToolbarDefaultBinding;
import com.github.lany192.arch.items.ItemsActivity;
import com.lany192.box.sample.data.binder.ArticleBinder;
import com.lany192.box.sample.ui.hello.HelloViewModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/goods")
public class GoodsActivity extends ItemsActivity<HelloViewModel, ActivityGoodsBinding, ToolbarDefaultBinding> {
    {
        register(new ArticleBinder());
    }

    @Override
    public SmartRefreshLayout getRefreshLayout() {
        return binding.refreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return binding.recyclerView;
    }
}