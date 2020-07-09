package com.github.lany192.box.sample.fragment.city;


import com.github.lany192.box.delegate.Delegate;
import com.github.lany192.box.mvp.BaseView;

import java.util.List;


public interface CityContract {

    interface View extends BaseView {

        void showCities(List<Delegate> items);
    }

    interface Presenter {

        void init();

        void request();
    }
}
