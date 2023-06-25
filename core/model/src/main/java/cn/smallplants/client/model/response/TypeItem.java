package cn.smallplants.client.model.response;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//举报类型
public class TypeItem implements Serializable {
    //举报类型
    @SerializedName("type")
    private int type;
    //举报类型名称
    @SerializedName("name")
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}