package cn.smallplants.client.network.repository

import cn.smallplants.client.model.response.MsgAttentionItem
import cn.smallplants.client.model.response.MsgCommentItem
import cn.smallplants.client.model.response.MsgMixtureItem
import cn.smallplants.client.model.response.MsgNoticeItem
import cn.smallplants.client.model.response.MsgSystemItem
import cn.smallplants.client.model.response.MsgUnread
import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class MessageRepository(private val service: ApiService) : BaseRepository() {
    /**
     * 未读消息
     */
    fun getUnreadCount(): Flow<ApiResult<MsgUnread>> {
        return request { service.getUnreadCount() }
    }

    /**
     * 清除未读
     */
    fun clearUnread(): Flow<ApiResult<Any>> {
        return request { service.clearUnread() }
    }

    /**
     * 互动消息
     */
    fun messageMixture(
        //筛选类型，0全部，1点赞，2@我的，3评论
        type: Int,
        //第几页
        pageNum: Int
    ): Flow<ApiResult<PageInfo<MsgMixtureItem>>> {
        return request { service.messageMixture(type, pageNum, 20) }
    }

    /**
     * 消息-关注
     */
    fun messageAttention(page: Int): Flow<ApiResult<PageInfo<MsgAttentionItem>>> {
        return request { service.messageAttention(page, 20) }
    }

    /**
     * 评论消息列表
     */
    fun messageComment(page: Int): Flow<ApiResult<PageInfo<MsgCommentItem>>> {
        return request { service.messageComment(page, 20) }
    }

    /**
     * 消息-系统公告
     */
    fun messageNotice(page: Int): Flow<ApiResult<PageInfo<MsgNoticeItem>>> {
        return request { service.messageNotice(page, 20) }
    }

    /**
     * 消息-系统公告
     */
    fun messageSystem(page: Int): Flow<ApiResult<PageInfo<MsgSystemItem>>> {
        return request { service.messageSystem(page, 20) }
    }
}