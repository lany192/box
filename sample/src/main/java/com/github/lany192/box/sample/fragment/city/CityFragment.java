package com.github.lany192.box.sample.fragment.city;

import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CityFragment extends Fragment implements CityContract.View {
    @Inject
    CityPresenter mCityPresenter;
}