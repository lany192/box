package com.github.lany192.box.sample.model;

import com.github.lany192.box.http.Callback;
import com.github.lany192.box.sample.bean.SimpleBean;

public interface Model {

    void getHello(String msg, Callback<SimpleBean> request);
}
