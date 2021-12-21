package com.github.lany192.box.sample.ui.main.index.city;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.items.PageListFragment;
import com.github.lany192.box.sample.data.binder.AreaBinder;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/fragment/city")
public class CityFragment extends PageListFragment<CityViewModel> {
    {
        register(new AreaBinder());
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