package cn.smallplants.client.model.v4;

import com.google.gson.annotations.SerializedName;

import cn.smallplants.client.model.response.PlantItem;
import cn.smallplants.client.model.response.UserItem;

public class MultiItem {
    //类型，1是用户，2植物,3长文,4美图
    @SerializedName("type")
    int type;

    //用户信息,type等于1时才有值
    @SerializedName("user")
    UserItem user;

    //植物信息,type等于2时才有值
    @SerializedName("plant")
    PlantItem plant;

    //文章信息,type等于3时才有值
    @SerializedName("article")
    ArticleItem article;

    //美图信息,type等于4时才有值
    @SerializedName("mettle")
    MettleItem mettle;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserItem getUser() {
        return user;
    }

    public void setUser(UserItem user) {
        this.user = user;
    }

    public PlantItem getPlant() {
        return plant;
    }

    public void setPlant(PlantItem plant) {
        this.plant = plant;
    }

    public ArticleItem getArticle() {
        return article;
    }

    public void setArticle(ArticleItem article) {
        this.article = article;
    }

    public MettleItem getMettle() {
        return mettle;
    }

    public void setMettle(MettleItem mettle) {
        this.mettle = mettle;
    }
}
