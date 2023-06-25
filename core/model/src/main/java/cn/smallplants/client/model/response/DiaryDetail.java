package cn.smallplants.client.model.response;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import cn.smallplants.client.model.v4.ImageItem;

//植物日记信息
public class DiaryDetail implements Serializable {
    //记录时间
    LocalDate recordDate;
    //作者
    UserItem author;
    //分享
    Share share;
    //日记id
    private long diaryId;
    //创建时间
    private long createTime;
    //日记描述
    private String content;
    //植物id
    private long plantId;
    //浏览量
    private long views;
    //评论数
    private long comments;
    //权限
    private int permission;
    //图片
    private List<ImageItem> images;
    //淘口令商品
    private List<GoodsItem> goods;

    public long getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(long diaryId) {
        this.diaryId = diaryId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getPlantId() {
        return plantId;
    }

    public void setPlantId(long plantId) {
        this.plantId = plantId;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public UserItem getAuthor() {
        return author;
    }

    public void setAuthor(UserItem author) {
        this.author = author;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public List<ImageItem> getImages() {
        return images;
    }

    public void setImages(List<ImageItem> images) {
        this.images = images;
    }

    public List<GoodsItem> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsItem> goods) {
        this.goods = goods;
    }
}
