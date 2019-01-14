package com.lany.box.sample.model;

import com.lany.box.http.ApiService;
import com.lany.box.http.Callback;
import com.lany.box.http.HttpRequest;
import com.lany.box.sample.bean.SimpleBean;

public class Repository implements Model {
    private ApiService mAPIService;

    public Repository(ApiService apiService) {
        mAPIService = apiService;
    }

    @Override
    public void getHello(String msg, Callback<SimpleBean> request) {
        HttpRequest.of("common/hello.html")
                .add("type", 1)
                .post(mAPIService)
                .subscribe(request);
    }
}
