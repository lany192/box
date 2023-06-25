package cn.smallplants.client.network.repository

import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.v4.MultiItem
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class SearchRepository(private val service: ApiService) : BaseRepository() {
    /**
     * 搜索热词
     */
    fun searchHotKeywords(): Flow<ApiResult<List<String>>> {
        return request { service.searchHotKeywords() }
    }

    /**
     * 搜索用户
     */
    fun searchUser(keyword: String, page: Int): Flow<ApiResult<PageInfo<MultiItem>>> {
        return request { service.searchUser(page, 20, keyword) }
    }

    /**
     * 搜索植物
     */
    fun searchPlant(
        //第几页
        pageNum: Int,
        //搜索关键字
        keyword: String,
        //搜索排序类型:0发布日期，1最近更新，2最多点赞
        sort: Int,
    ): Flow<ApiResult<PageInfo<MultiItem>>> {
        return request { service.searchPlant(pageNum, 20, keyword, sort) }
    }

    /**
     * 搜索长文
     */
    fun searchArticle(
        //第几页
        pageNum: Int,
        //搜索关键字
        keyword: String,
        //搜索排序类型:0发布日期，1最近更新，2最多点赞
        sort: Int,
    ): Flow<ApiResult<PageInfo<MultiItem>>> {
        return request { service.searchArticle(pageNum, 20, keyword, sort) }
    }

    /**
     * 搜索美图
     */
    fun searchMettle(
        //第几页
        pageNum: Int,
        //搜索关键字
        keyword: String,
        //搜索排序类型:0发布日期，1最近更新，2最多点赞
        sort: Int,
    ): Flow<ApiResult<PageInfo<MultiItem>>> {
        return request { service.searchMettle(pageNum, 20, keyword, sort) }
    }
}