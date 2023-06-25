package cn.smallplants.client.network.repository

import cn.smallplants.client.model.response.LikeResult
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class UpvoteRepository(private val service: ApiService) : BaseRepository() {

    /**
     * 点赞评论
     */
    fun upvoteComment(
        //评论id
        commentId: Long,
        //false表示取消点赞，true表示添加点赞
        upvote: Boolean
    ): Flow<ApiResult<LikeResult>> {
        return request { service.upvoteComment(commentId, upvote) }
    }

    /**
     * 点赞回复
     */
    fun upvoteReply(
        //评论id
        commentId: Long,
        //回复id
        replyId: Long,
        //false表示取消点赞，true表示添加点赞
        upvote: Boolean
    ): Flow<ApiResult<LikeResult>> {
        return request { service.upvoteReply(commentId, replyId, upvote) }
    }
}