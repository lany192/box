package com.github.lany192.box.sample;

import com.github.lany192.box.mvp.BasePresenter;
import com.github.lany192.box.sample.http.ApiService;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainActivity, ApiService> implements MainContract.Presenter {

    @Inject
    public MainPresenter(MainActivity view, ApiService model) {
        super(view, model);
    }
}
