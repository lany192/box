package cn.smallplants.client.model.response;

import com.google.gson.annotations.SerializedName;

public class MsgNoticeItem {
    //点赞消息id
    @SerializedName("id")
    private long id;

    //内容
    @SerializedName("message")
    private String message;

    //创建时间
    @SerializedName("createTime")
    private long createTime;

    //封面，可能为空
    @SerializedName("cover")
    private String cover;

    //标题,可能为空
    @SerializedName("title")
    private String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
