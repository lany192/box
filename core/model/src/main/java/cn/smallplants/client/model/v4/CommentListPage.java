package cn.smallplants.client.model.v4;


import com.google.gson.annotations.SerializedName;

import cn.smallplants.client.model.response.PageInfo;

public class CommentListPage extends PageInfo<CommentItem> {
    //被置顶的评论，有值的条件：1.请求参数有指定的评论id 2.只出现在第一页
    @SerializedName("topComment")
    private CommentItem topComment;

    public CommentItem getTopComment() {
        return topComment;
    }

    public void setTopComment(CommentItem topComment) {
        this.topComment = topComment;
    }
}
