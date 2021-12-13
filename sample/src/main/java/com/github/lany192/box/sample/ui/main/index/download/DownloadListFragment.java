package com.github.lany192.box.sample.ui.main.index.download;

import com.github.lany192.box.items.PageListFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DownloadListFragment extends PageListFragment<DownloadListViewModel> {
    {
        register(new DownloadBinder());
    }

    @Override
    public int getItemSpanSize(int viewType, int position) {
        return 1;
    }
}