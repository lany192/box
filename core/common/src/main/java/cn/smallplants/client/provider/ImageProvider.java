package cn.smallplants.client.provider;


import com.alibaba.android.arouter.facade.template.IProvider;

import java.util.List;

public interface ImageProvider extends IProvider {

    void startPreview(int index, List<String> images, long ownerId);
}
