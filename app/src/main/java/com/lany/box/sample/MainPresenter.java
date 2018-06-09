package com.lany.box.sample;

import com.lany.box.mvp.presenter.BasePresenter;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainActivity, MainModel> implements MainContract.Presenter {

    @Inject
    public MainPresenter(MainActivity view, MainModel model) {
        super(view, model);
    }

    @Override
    public void sayClick() {
        getView().sayHello(getModel().getContent());
    }
}
