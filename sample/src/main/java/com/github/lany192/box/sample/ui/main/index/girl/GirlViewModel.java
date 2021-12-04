package com.github.lany192.box.sample.ui.main.index.girl;

import androidx.lifecycle.MutableLiveData;

import com.github.lany192.box.mvvm.LifecycleViewModel;
import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.http.ApiService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class GirlViewModel extends LifecycleViewModel {
    private final MutableLiveData<List<String>> items = new MutableLiveData<>();
    private final ApiService apiService;

    @Inject
    public GirlViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public MutableLiveData<List<String>> getItems() {
        return items;
    }

    @Override
    protected void onLazyLoad() {
        requestCityInfo();
    }

    public void requestCityInfo() {
        showLoading(true);
        List<String> images = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            images.add(MockUtils.getImageUrl());
        }
        showLoading(false);

        items.postValue(images);
    }

}
