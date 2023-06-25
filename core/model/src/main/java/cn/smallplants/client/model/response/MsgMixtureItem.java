package cn.smallplants.client.model.response;


import java.io.Serializable;

//互动混合消息
public class MsgMixtureItem implements Serializable {
    //功能类型（0：植记，1：长文：2晒图）V2新版本增加
    Integer functionType;
    //点赞消息,type等于1时才有值
    MsgLikeItem like;
    //评论/回复 消息,type等于2时才有值
    MsgCommentItem comment;
    //@我消息,type等于3时才有值
    MsgAtItem atme;
    //类型，1是点赞，2评论，3@我
    private int type;

    public MsgMixtureItem(MsgLikeItem like) {
        this.type = 1;
        this.like = like;
    }

    public MsgMixtureItem(MsgCommentItem comment) {
        this.type = 2;
        this.comment = comment;
    }

    public MsgMixtureItem(MsgAtItem atme) {
        this.type = 3;
        this.atme = atme;
    }

    public int getType() {
        return type;
    }

    public Integer getFunctionType() {
        return functionType;
    }

    public MsgLikeItem getLike() {
        return like;
    }

    public MsgCommentItem getComment() {
        return comment;
    }

    public MsgAtItem getAtme() {
        return atme;
    }
}
