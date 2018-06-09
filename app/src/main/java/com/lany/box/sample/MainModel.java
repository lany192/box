package com.lany.box.sample;

import com.lany.box.mvp.model.BaseModel;

import javax.inject.Inject;


public class MainModel extends BaseModel implements MainContract.Model {

    @Inject
    public MainModel() {

    }

    @Override
    public String getContent() {
        return "哈哈，我来之MainModel";
    }
}
