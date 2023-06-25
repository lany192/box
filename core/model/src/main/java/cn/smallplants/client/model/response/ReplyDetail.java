package cn.smallplants.client.model.response;


//回复详情
public class ReplyDetail extends ReplyItem {
    //所属植物的评论总数
    private long plantCommentCount;

    public long getPlantCommentCount() {
        return plantCommentCount;
    }

    public void setPlantCommentCount(long plantCommentCount) {
        this.plantCommentCount = plantCommentCount;
    }
}
