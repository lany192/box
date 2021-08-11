package com.github.lany192.box.sample.fragment.city;

import com.github.lany192.box.delegate.Delegate;
import com.github.lany192.box.fragment.FragmentContract;
import com.github.lany192.box.mvp.BaseContract;

import java.util.List;

public interface CityContract {

    interface View extends FragmentContract.View {

        void showCities(List<Delegate> items);
    }

    interface Presenter extends BaseContract.Presenter {

        void init();

        void request();
    }

    interface Model  extends BaseContract.Model {

    }
}
