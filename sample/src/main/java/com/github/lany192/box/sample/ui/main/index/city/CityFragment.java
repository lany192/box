package com.github.lany192.box.sample.ui.main.index.city;

import com.github.lany192.box.items.ItemsFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CityFragment extends ItemsFragment<CityViewModel> {
    {
        register(new AreaBinder());
    }
}