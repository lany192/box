package cn.smallplants.client.network.repository

import cn.smallplants.client.model.response.HomeDetail
import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.v4.MultiItem
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class HomeRepository(private val service: ApiService) : BaseRepository() {
    /**
     * 个人主页基本信息
     */
    fun homeUserInfo(
        //目标id
        id: Long
    ): Flow<ApiResult<HomeDetail>> {
        return request { service.homeUserInfo(id) }
    }

    /**
     * 个人主页-用户发布的
     */
    fun homePublish(
        //用户id
        id: Long,
        //排序类型，0发布日期，1最近更新，2最多点赞
        sort: Int,
        //类型：0植物，1长文，2晒图
        type: Int,
        //第几页
        pageNum: Int
    ): Flow<ApiResult<PageInfo<MultiItem>>> {
        return request { service.homePublish(id, sort, type, pageNum, 20) }
    }

    /**
     * 个人主页-喜欢的植物
     */
    fun homeEnjoy(
        //用户id
        id: Long,
        //排序类型，0发布日期，1最近更新，2最多点赞
        sort: Int,
        //第几页
        pageNum: Int
    ): Flow<ApiResult<PageInfo<MultiItem>>> {
        return request { service.homeEnjoy(id, sort, pageNum, 20) }
    }
}