package cn.smallplants.client.provider;


import com.alibaba.android.arouter.facade.template.IProvider;

public interface PlantProvider extends IProvider {

    void startPlantDetail(long pid, long cid, long rid);

    void startPlantCreate();

    void startDiaryCreate(long plantId);
}
