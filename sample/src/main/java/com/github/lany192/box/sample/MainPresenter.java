package com.github.lany192.box.sample;

import com.github.lany192.box.mvp.presenter.BasePresenter;
import com.github.lany192.box.sample.model.Model;
import com.github.lany192.box.sample.model.Repository;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainActivity, Model> implements MainContract.Presenter {

    @Inject
    public MainPresenter(MainActivity view, Repository model) {
        super(view, model);
    }
}
