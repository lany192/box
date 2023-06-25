package cn.smallplants.client.model.response;

import java.io.Serializable;


public class ImageTime implements Serializable {
    private String key;
    private long value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
