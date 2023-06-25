package cn.smallplants.client.provider;


import com.alibaba.android.arouter.facade.template.IProvider;

public interface MettleProvider extends IProvider {

    void startMettleDetail(long pid, long cid, long rid);

    void startMettleCreate();

    void startDiaryCreate(long plantId);
}
