package com.github.lany192.box.sample.model;

import com.github.lany192.box.http.ApiService;
import com.github.lany192.box.http.Callback;
import com.github.lany192.box.http.HttpRequest;
import com.github.lany192.box.sample.bean.SimpleBean;

public class Repository implements Model {
    private ApiService mAPIService;

    public Repository(ApiService apiService) {
        mAPIService = apiService;
    }

    @Override
    public void getHello(String msg, Callback<SimpleBean> request) {
        HttpRequest.of("common/hello.html").service(mAPIService)
                .add("type", 1)
                .post()
                .subscribe(request);
    }
}
