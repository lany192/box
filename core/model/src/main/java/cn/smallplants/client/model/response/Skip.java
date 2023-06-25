package cn.smallplants.client.model.response;


import java.io.Serializable;

public class Skip implements Serializable {
    /**
     * 跳转类型
     */
    private int type;
    /**
     * 跳转标题
     */
    private String title;
    /**
     * 跳转路径
     */
    private String url;
    /**
     * 跳转参数
     */
    private String value;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
