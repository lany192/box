package com.github.lany192.box.sample.fragment.city;


import com.github.lany192.box.delegate.ItemDelegate;
import com.github.lany192.box.mvp.view.BaseView;

import java.util.List;


public interface CityContract {

    interface View extends BaseView {

        void showCities(List<ItemDelegate> items);
    }

    interface Presenter {
        void init();
    }
}
