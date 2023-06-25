package cn.smallplants.client.model.v4;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//图片
public class ImageItem implements Serializable {
    //主键id
    @SerializedName("id")
    private long id;

    //名称
    @SerializedName("name")
    private String name;

    //缩列图,宽度最大800
    @SerializedName("url")
    private String url;

    //正常图
    @SerializedName("largeUrl")
    private String largeUrl;

    //图片宽度
    @SerializedName("width")
    private int width;

    //图片高度
    @SerializedName("height")
    private int height;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
