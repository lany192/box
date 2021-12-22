package com.github.lany192.box.sample.ui.main.index.city;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.databinding.FragmentPageBinding;
import com.github.lany192.arch.items.PageListFragment;
import com.github.lany192.box.sample.data.binder.AreaBinder;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/fragment/city")
public class CityFragment extends PageListFragment<CityViewModel, FragmentPageBinding> {
    {
        register(new AreaBinder());
    }

    @Override
    public SmartRefreshLayout getRefreshLayout() {
        return binding.refreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return binding.recyclerView;
    }
    @Override
    public int getItemSpanSize(int viewType, int position) {
        return 1;
    }

    @Override
    public void initView() {
        super.initView();
    }
}