package cn.smallplants.client.provider;


import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface IndexProvider extends IProvider {

    /**
     * 首页
     */
    Fragment getMainIndex();
}
