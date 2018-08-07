package com.lany.box.sample.model;

import com.lany.box.http.Callback;
import com.lany.box.sample.bean.SimpleBean;

public interface Model {

    void getHello(String msg, Callback<SimpleBean> request);
}
