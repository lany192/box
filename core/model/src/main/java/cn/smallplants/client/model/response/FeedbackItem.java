package cn.smallplants.client.model.response;


import java.io.Serializable;
import java.util.List;

import cn.smallplants.client.model.entity.Picture;

//反馈记录
public class FeedbackItem implements Serializable {
    //记录id
    private long id;
    //创建时间
    private long createTime;
    //联系方式
    private String contact;
    //详细地址
    private String detail;
    //处理结果
    private String result;
    private List<Picture> images;

    public List<Picture> getImages() {
        return images;
    }

    public void setImages(List<Picture> images) {
        this.images = images;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
