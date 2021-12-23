package com.github.lany192.box.sample.ui.main.discover;

import android.os.Handler;

import com.github.lany192.arch.items.ListViewModel;
import com.github.lany192.box.sample.MockUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DiscoverViewModel extends ListViewModel {
    int count;

    @Inject
    public DiscoverViewModel() {
    }

    @Override
    public void request(boolean refresh) {
        if (refresh) {
            count = 0;
        }
        if (count < 5) {
            List<String> images = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                images.add(MockUtils.getImageUrl());
            }
            new Handler().postDelayed(() -> {
                if (refresh) {
                    resetItems(images);
                    refreshFinish();
                } else {
                    addItems(images);
                    moreLoadFinish();
                }
                count++;
            }, 3000);
        } else {
            moreLoadEnd();
        }

    }
}
