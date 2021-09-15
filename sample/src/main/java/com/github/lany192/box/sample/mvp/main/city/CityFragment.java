package com.github.lany192.box.sample.mvp.main.city;

import com.github.lany192.box.binding.BindingFragment;
import com.github.lany192.box.sample.databinding.FragmentCityBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CityFragment extends BindingFragment<FragmentCityBinding> implements CityContract.View {
    @Inject
    CityPresenter presenter;

    @Override
    public void onResume() {
        super.onResume();
        presenter.requestCityInfo();
    }
}