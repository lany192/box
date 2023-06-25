package cn.smallplants.client.network.repository

import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.v4.MettleDetail
import cn.smallplants.client.model.v4.MettleItem
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class MettleRepository(private val service: ApiService) : BaseRepository() {
    /**
     * 创建美图
     */
    fun mettleBuild(
        //晒图标题
        title: String,
        //晒图内容
        content: String,
        //要关联的图片ids
        imageIds: List<Long>
    ): Flow<ApiResult<Long>> {
        return request { service.mettleBuild(title, content, imageIds) }
    }

    /**
     * 发布美图
     */
    fun mettlePublish(mettleId: Long): Flow<ApiResult<Any>> {
        return request { service.mettlePublish(mettleId) }
    }

    /**
     * 编辑美图
     */
    fun mettleEdit(
        //晒图ID
        mettleId: Long,
        //晒图标题
        title: String,
        //晒图标题
        content: String,
        //要关联的图片ids
        imageIds: List<Long>
    ): Flow<ApiResult<Long>> {
        return request { service.mettleEdit(mettleId, title, content, imageIds) }
    }

    /**
     * 晒图详情
     */
    fun mettleDetail(mettleId: Long): Flow<ApiResult<MettleDetail>> {
        return request { service.mettleDetail(mettleId) }
    }

    /**
     * 删除美图
     */
    fun mettleDelete(mettleId: Long): Flow<ApiResult<Any>> {
        return request { service.mettleDelete(mettleId) }
    }

    /**
     * 美图草稿
     */
    fun mettleDrafts(page: Int): Flow<ApiResult<PageInfo<MettleItem>>> {
        return request { service.mettleDrafts(page, 20) }
    }
}