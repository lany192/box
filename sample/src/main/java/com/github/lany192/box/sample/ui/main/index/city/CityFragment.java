package com.github.lany192.box.sample.ui.main.index.city;

import com.github.lany192.arch.items.PageListFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
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