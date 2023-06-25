package cn.smallplants.client.provider;


import com.alibaba.android.arouter.facade.template.IProvider;

public interface GoodsProvider extends IProvider {

    void startGoodsList();

    void startCreateGoods();

    void startEditGoods();

    void startGoodsDetail(long goodsId);
}
