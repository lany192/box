package cn.smallplants.client.provider;


import com.alibaba.android.arouter.facade.template.IProvider;

public interface CommentProvider extends IProvider {

    void startCommentDetail(long commentId, long authorId);

    void startPlantComment(long plantId, long count);
}
