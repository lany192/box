package cn.smallplants.client.network.repository

import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.v4.Location
import cn.smallplants.client.model.v4.MultiItem
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class IndexRepository(private val service: ApiService) : BaseRepository() {
    /**
     * 首页-爱看
     */
    fun indexEnjoy(
        //功能类型：0：植物，1：长文，2：晒图
        type: Int,
        //第几页
        pageNum: Int
    ): Flow<ApiResult<PageInfo<MultiItem>>> {
        return request { service.indexEnjoy(type, pageNum, 20) }
    }

    /**
     * 首页-推荐
     */
    fun indexRecommend(
        //第几页
        pageNum: Int
    ): Flow<ApiResult<PageInfo<MultiItem>>> {
        return request { service.indexRecommend(pageNum, 20) }
    }

    /**
     * 首页-关注
     */
    fun indexAttention(page: Int): Flow<ApiResult<PageInfo<MultiItem>>> {
        return request { service.indexAttention(page, 20) }
    }

    /**
     * 首页-同城
     */
    fun indexLocation(page: Int): Flow<ApiResult<Location>> {
        return request { service.indexLocation(page, 20) }
    }
}