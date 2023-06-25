package cn.smallplants.client.network.repository

import cn.smallplants.client.model.response.AttentionResult
import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.response.UserItem
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class AttentionRepository(private val service: ApiService) : BaseRepository() {
    /**
     * 添加/取消 关注用户
     */
    fun attentionUser(
        //0表示取消关注，1表示关注用户
        type: Int,
        //目标用户id
        id: Long
    ): Flow<ApiResult<AttentionResult>> {
        return request { service.attentionUser(type, id) }
    }

    /**
     * 已关注用户列表
     */
    fun attentionUserList(
        //是否是粉丝列表
        fans: Boolean,
        //用户id
        ownerId: Long,
        //第几页
        pageNum: Int
    ): Flow<ApiResult<PageInfo<UserItem>>> {
        return request { service.attentionUserList(fans, ownerId, pageNum, 20) }
    }
}