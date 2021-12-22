package com.github.lany192.box.sample.ui.main.discover;

import android.os.Handler;

import com.github.lany192.arch.items.PageListViewModel;
import com.github.lany192.arch.items.UIState;
import com.github.lany192.box.sample.MockUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DiscoverViewModel extends PageListViewModel {
    int count;

    @Inject
    public DiscoverViewModel() {
    }

    @Override
    public void request(boolean refresh) {
        if (refresh) {
            count = 0;
        }
        if (count < 2) {
            showLoading(true);
            List<String> images = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                images.add(MockUtils.getImageUrl());
            }
            new Handler().postDelayed(() -> {
                showLoading(false);
                if (refresh) {
                    resetItems(images);
                    finishRefresh();
                } else {
                    addItems(images);
                    finishLoadMore();
                }
                count++;
            }, 3000);
        } else {
            changeState(UIState.MORE_LOAD_END);
        }

    }
}
