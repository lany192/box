package cn.smallplants.client.model.response;


import java.io.Serializable;

//系统消息
public class MsgSystemItem implements Serializable {
    //点赞消息id
    private long id;
    //内容
    private String message;
    //创建时间
    private long createTime;
    //详情链接，可能为空
    private String url;
    //封面，可能为空
    private String cover;
    //标题,可能为空
    private String title;

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getUrl() {
        return url;
    }

    public String getCover() {
        return cover;
    }

    public String getTitle() {
        return title;
    }
}
