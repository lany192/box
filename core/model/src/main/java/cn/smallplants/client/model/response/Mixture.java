package cn.smallplants.client.model.response;


import java.io.Serializable;

@Deprecated
//多类型item信息
public class Mixture implements Serializable {
    //植物信息,type等于2时才有值
    PlantItem plant;
    //广告信息,type等于1时才有值
    BannerItem banner;
    //产品信息,type等于3时才有值
    String product;
    //类型，1是广告，2植物,3产品
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public PlantItem getPlant() {
        return plant;
    }

    public void setPlant(PlantItem plant) {
        this.plant = plant;
    }

    public BannerItem getBanner() {
        return banner;
    }

    public void setBanner(BannerItem banner) {
        this.banner = banner;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
