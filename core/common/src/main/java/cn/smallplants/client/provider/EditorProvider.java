package cn.smallplants.client.provider;


import com.alibaba.android.arouter.facade.template.IProvider;

public interface EditorProvider extends IProvider {

    void startRichEditor(String title, String content);
}
