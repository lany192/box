package cn.smallplants.client.network.repository

import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.response.ReportItem
import cn.smallplants.client.model.response.TypeItem
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class ReportRepository(private val service: ApiService) : BaseRepository() {

    /**
     * 举报类型
     */
    fun reportTypes(): Flow<ApiResult<List<TypeItem>>> {
        return request { service.reportTypes() }
    }

    /**
     * 举报记录
     */
    fun reportRecords(page: Int): Flow<ApiResult<PageInfo<ReportItem>>> {
        return request { service.reportRecords(page, 20) }
    }

    /**
     * 举报评论
     */
    fun reportComment(
        //评论id
        id: Long,
        //举报类型,从类型接口中选择
        type: Int,
        //举报内容
        content: String,
        //图片id，可选项
        picIds: List<Long>
    ): Flow<ApiResult<Any>> {
        return request { service.reportComment(id, type, content, picIds) }
    }

    /**
     * 举报用户
     */
    fun reportUser(
        //被举报人id
        id: Long,
        //举报类型,从类型接口中选择
        type: Int,
        //举报内容
        content: String,
        //图片id，可选项
        picIds: List<Long>
    ): Flow<ApiResult<Any>> {
        return request { service.reportUser(id, type, content, picIds) }
    }

    /**
     * 举报植物
     */
    fun reportPlant(
        //植物id
        id: Long,
        //举报类型,从类型接口中选择
        type: Int,
        //举报内容
        content: String,
        //图片id，可选项
        picIds: List<Long>
    ): Flow<ApiResult<Any>> {
        return request { service.reportPlant(id, type, content, picIds) }
    }

    /**
     * 举报长文
     */
    fun reportArticle(
        //长文id
        id: Long,
        //举报类型,从类型接口中选择
        type: Int,
        //举报内容
        content: String,
        //图片id，可选项
        picIds: List<Long>
    ): Flow<ApiResult<Any>> {
        return request { service.reportArticle(id, type, content, picIds) }
    }

    /**
     * 举报晒图
     */
    fun reportMettle(
        //晒图id
        id: Long,
        //举报类型,从类型接口中选择
        type: Int,
        //举报内容
        content: String,
        //图片id，可选项
        picIds: List<Long>
    ): Flow<ApiResult<Any>> {
        return request { service.reportMettle(id, type, content, picIds) }
    }
}