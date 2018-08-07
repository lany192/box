package com.lany.box.sample.model;

import com.lany.box.http.ApiService;
import com.lany.box.http.Callback;
import com.lany.box.http.HttpRequest;
import com.lany.box.sample.bean.SimpleBean;

public class Repository implements Model {
    private ApiService mAPIService;

    public Repository(ApiService apiService) {
        mAPIService = apiService;
        HttpRequest.addDefaultParams("uid", "123456");
        HttpRequest.addDefaultParams("token", "sdjfsh4324jkdh3234r4r4r4y5y6u6w34fff");
    }

    @Override
    public void getHello(String msg, Callback<SimpleBean> request) {
        HttpRequest.of("common/hello.html")
                .add("type", 1)
                .post(mAPIService)
                .subscribe(request);
    }
}
