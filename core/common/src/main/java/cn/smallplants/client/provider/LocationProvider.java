package cn.smallplants.client.provider;


import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface LocationProvider extends IProvider {

    /**
     * 同城
     */
    Fragment getLocation();
}
