package com.github.lany192.box.sample.ui.main.index.city;

import com.github.lany192.box.items.PageListFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CityListFragment extends PageListFragment<CityListViewModel> {
    {
        register(new AreaBinder());
    }

    @Override
    public int getItemSpanSize(int viewType, int position) {
        return 1;
    }
}