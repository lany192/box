package cn.smallplants.client.model.response;


import java.io.Serializable;

/**
 * @author lany
 */

//删除结果
public class DeleteInfo implements Serializable {
    //植物评论总数，包括子评论数量
    private long plantCommentCount;
}
