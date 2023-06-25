package cn.smallplants.client.model.entity;

import java.io.Serializable;

public class Picture implements Serializable {
    //宽度
    private int width;
    //宽度
    private int height;
    //图片名称
    private String name;
    //图片拍摄时间，可以为空，为空代表当前，传入格式为时间戳
    private String time;
    //图片格式
    private String format;
    //图片大小
    private long size;
    //OSS图片地址
    private String urlAddress;

    private String path;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
