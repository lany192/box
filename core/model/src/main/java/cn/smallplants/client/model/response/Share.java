package cn.smallplants.client.model.response;


import java.io.Serializable;

//分享信息
public class Share implements Serializable {
    //分享的内容标题
    private String title;
    //分享的文本内容
    private String content;
    //分享的图片地址
    private String image;
    //分享用的h5地址
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
