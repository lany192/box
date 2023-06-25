package cn.smallplants.client.network.repository

import cn.smallplants.client.model.response.DeleteInfo
import cn.smallplants.client.model.response.ReplyDetail
import cn.smallplants.client.model.v4.CommentListPage
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class CommentRepository(private val service: ApiService) : BaseRepository() {

    /**
     * 评论列表
     */
    fun commentsList(
        //类型：0植物，1长文，2晒图
        type: Int,
        //目标id
        id: Long,
        //需要置顶的评论id，可选项
        topCommentId: Long,
        //第几页
        pageNum: Int
    ): Flow<ApiResult<CommentListPage>> {
        return request { service.commentsList(type, id, topCommentId, pageNum, 20) }
    }

    /**
     * 评论发布
     */
    fun commentPublish(
        //类型：0植物，1长文，2晒图
        type: Int,
        //目标id
        id: Long,
        //评论内容
        content: String,
        //图片ids，可选项
        picIds: List<Long>
    ): Flow<ApiResult<Any>> {
        return request { service.commentPublish(type, id, content, picIds) }
    }

    /**
     * 评论详情
     */
    fun commentDetail(
        //目标id
        id: Long,
        //需要置顶的评论id，可选项
        topReplyId: Long,
        //第几页
        pageNum: Int
    ): Flow<ApiResult<Any>> {
        return request { service.commentDetail(id, topReplyId, pageNum, 20) }
    }

    /**
     * 删除评论
     */
    fun commentDelete(
        //目标id
        commentId: Long
    ): Flow<ApiResult<DeleteInfo>> {
        return request { service.commentDelete(commentId) }
    }

    /**
     * 发布回复
     */
    fun publishReply(
        //评论id
        commentId: Long,
        //评论内容
        content: String,
        //被回复的回复id（选填）
        toReplyId: Long
    ): Flow<ApiResult<ReplyDetail>> {
        return request { service.publishReply(commentId, content, toReplyId) }
    }

    /**
     * 删除回复
     */
    fun deleteReply(
        //目标id
        id: Long
    ): Flow<ApiResult<DeleteInfo>> {
        return request { service.deleteReply(id) }
    }
}