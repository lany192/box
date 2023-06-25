package cn.smallplants.client.provider;


import com.alibaba.android.arouter.facade.template.IProvider;

public interface BrowserProvider extends IProvider {

    void startBrowser(String title, String url);
}
