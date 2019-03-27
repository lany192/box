package com.lany.box.sample.model;

import com.lany.box.http.ApiService;
import com.lany.box.http.Callback;
import com.lany.box.http.Request;
import com.lany.box.sample.bean.SimpleBean;

public class Repository implements Model {
    private ApiService mAPIService;

    public Repository(ApiService apiService) {
        mAPIService = apiService;
    }

    @Override
    public void getHello(String msg, Callback<SimpleBean> request) {
        Request.of("common/hello.html").service(mAPIService)
                .add("type", 1)
                .post()
                .subscribe(request);
    }
}
