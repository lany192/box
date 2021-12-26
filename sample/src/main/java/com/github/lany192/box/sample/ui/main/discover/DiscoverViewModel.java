package com.github.lany192.box.sample.ui.main.discover;

import android.os.Handler;

import com.github.lany192.arch.items.ListViewModel;
import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.data.bean.Category;

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
            List<Object> images = new ArrayList<>();
            if (refresh) {
                for (int i = 0; i < 4; i++) {
                    images.add(new Category(i, "分类" + i, MockUtils.getImageUrl()));
                }
            }
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
