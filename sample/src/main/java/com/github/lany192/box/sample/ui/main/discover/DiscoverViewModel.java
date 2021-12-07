package com.github.lany192.box.sample.ui.main.discover;

import androidx.lifecycle.MutableLiveData;

import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.viewmodel.LifecycleViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DiscoverViewModel extends LifecycleViewModel {
    private final MutableLiveData<List<String>> items = new MutableLiveData<>();

    @Inject
    public DiscoverViewModel() {
        requestCityInfo();
    }

    public MutableLiveData<List<String>> getItems() {
        return items;
    }

    public void requestCityInfo() {
        showLoading(true);
        List<String> images = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            images.add(MockUtils.getImageUrl());
        }
        items.postValue(images);
        showLoading(false);
    }
}
