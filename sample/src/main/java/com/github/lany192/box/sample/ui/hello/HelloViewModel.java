package com.github.lany192.box.sample.ui.hello;

import com.github.lany192.arch.items.ListViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HelloViewModel extends ListViewModel {
    @Inject
    public HelloViewModel() {

    }

    @Override
    public void request(boolean refresh) {

    }
}
