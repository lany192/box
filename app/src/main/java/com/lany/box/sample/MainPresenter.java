package com.lany.box.sample;

import com.lany.box.http.Callback;
import com.lany.box.mvp.presenter.BasePresenter;
import com.lany.box.sample.bean.SimpleBean;
import com.lany.box.sample.model.Model;
import com.lany.box.sample.model.Repository;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainActivity, Model> implements MainContract.Presenter {

    @Inject
    public MainPresenter(MainActivity view, Repository model) {
        super(view, model);
    }

    @Override
    public void sayClick() {
        getModel().getHello("hello Lany", new Callback<SimpleBean>() {
            @Override
            public void onSuccess(SimpleBean bean) {
                getView().sayHello(bean.getMsg());
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }
}
