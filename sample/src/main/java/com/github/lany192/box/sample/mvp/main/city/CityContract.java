package com.github.lany192.box.sample.mvp.main.city;

import com.github.lany192.box.sample.bean.Area;

import java.util.List;

public interface CityContract {

    interface View {
        void showItems(List<Area> items);
    }

    interface Presenter {

        void requestCityInfo();
    }

    interface Model {

    }
}
