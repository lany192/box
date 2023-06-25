package cn.smallplants.client.network.repository

import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.response.UserItem
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class BlackRepository(private val service: ApiService) : BaseRepository() {

    /**
     * 编辑黑名单用户
     */
    fun blackEdit(
        //0表示将用户移除黑名单，1表示将用户加入黑名单
        type: Int,
        //目标id
        id: Long
    ): Flow<ApiResult<Any>> {
        return request { service.blackEdit(type, id) }
    }

    /**
     * 我的黑名单
     */
    fun blackRecords(
        //第几页
        pageNum: Int,
        //每页数量
        pageSize: Int
    ): Flow<ApiResult<PageInfo<UserItem>>> {
        return request { service.blackRecords(pageNum, 20) }
    }
}