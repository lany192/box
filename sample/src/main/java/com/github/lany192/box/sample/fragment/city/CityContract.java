package com.github.lany192.box.sample.fragment.city;


import com.github.lany192.box.delegate.Delegate;
import com.github.lany192.box.mvp.PageView;

import java.util.List;


public interface CityContract {

    interface View extends PageView {

        void showCities(List<Delegate> items);
    }

    interface Presenter {

        void init();

        void request();
    }
}
