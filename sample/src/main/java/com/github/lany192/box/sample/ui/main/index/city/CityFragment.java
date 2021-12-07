package com.github.lany192.box.sample.ui.main.index.city;

import com.github.lany192.box.items.PageFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CityFragment extends PageFragment<CityViewModel> {
    {
        register(new AreaBinder());
    }
}