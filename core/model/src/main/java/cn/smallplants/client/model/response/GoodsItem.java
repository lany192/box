package cn.smallplants.client.model.response;


import java.io.Serializable;

//淘口令商品
public class GoodsItem implements Serializable {
    //商品id
    private long id;
    //联系方式
    private String title;
    //详细地址
    private String code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}