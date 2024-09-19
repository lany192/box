package com.lany192.box.sample.ui.goods;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.items.ItemsActivity;
import com.github.lany192.arch.utils.BarUtils;
import com.lany192.box.sample.data.binder.ArticleBinder;
import com.lany192.box.sample.data.binder.ViewPagerBinder;
import com.lany192.box.sample.databinding.ActivityGoodsBinding;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/goods")
public class GoodsActivity extends ItemsActivity<GoodsViewModel, ActivityGoodsBinding> {
    {
        register(new ArticleBinder());
        register(new ViewPagerBinder());
    }

//    @Override
//    public void initImmersionBar() {
//        BarUtils.init(this).keyboardEnable(true).titleBar(binding.toolbar).init();
//    }

    @NonNull
    @Override
    public SmartRefreshLayout createRefreshLayout() {
        return binding.refreshLayout;
    }

    @NonNull
    @Override
    public RecyclerView createRecyclerView() {
        return binding.recyclerView;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                assert manager != null;
                int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                EventBus.getDefault().post(new DemoEvent(firstVisibleItemPosition));
            }
        });
    }
}