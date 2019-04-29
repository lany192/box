package com.lany.box.sample;

import com.lany.box.mvp.presenter.BasePresenter;
import com.lany.box.sample.model.Model;
import com.lany.box.sample.model.Repository;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainActivity, Model> implements MainContract.Presenter {

    @Inject
    public MainPresenter(MainActivity view, Repository model) {
        super(view, model);
    }
}
