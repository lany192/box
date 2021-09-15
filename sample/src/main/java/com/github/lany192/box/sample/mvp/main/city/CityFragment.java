package com.github.lany192.box.sample.mvp.main.city;

import com.github.lany192.box.binding.BindingFragment;
import com.github.lany192.box.sample.databinding.FragmentCityBinding;
import com.github.lany192.box.sample.http.ApiService;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CityFragment extends BindingFragment<FragmentCityBinding> implements CityContract.View {
    @Inject
    CityPresenter mCityPresenter;
    @Inject
    ApiService apiService;

    @Override
    public void onResume() {
        super.onResume();
//        apiService.cityInfo(new C)
    }
}