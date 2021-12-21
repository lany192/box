package com.github.lany192.box.sample.ui.main.discover;

import android.os.Handler;

import com.github.lany192.arch.items.PageListViewModel;
import com.github.lany192.box.sample.MockUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DiscoverViewModel extends PageListViewModel {
    @Inject
    public DiscoverViewModel() {
    }

    @Override
    public void request(boolean refresh) {
        showLoading(true);
        List<String> images = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            images.add(MockUtils.getImageUrl());
        }
        new Handler().postDelayed(() -> {
            showLoading(false);
            resetItems(images);
        }, 1000);
    }
}
