package cn.smallplants.client.network.repository

import cn.smallplants.client.model.response.FeedbackItem
import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.response.TypeItem
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class FeedbackRepository(private val service: ApiService) : BaseRepository() {

    /**
     * 反馈类型
     */
    fun feedbackTypes(): Flow<ApiResult<List<TypeItem>>> {
        return request { service.feedbackTypes() }
    }

    /**
     * 反馈记录
     */
    fun feedbackRecords(page: Int): Flow<ApiResult<PageInfo<FeedbackItem>>> {
        return request { service.feedbackRecords(page, 20) }
    }

    /**
     * 发布反馈
     */
    fun feedbackPublish(
        //类型,从类型接口中选择
        type: Int,
        //举报内容
        content: String,
        //联系方式
        contact: String,
        //图片id，可选项
        picIds: List<Long>
    ): Flow<ApiResult<Any>> {
        return request { service.feedbackPublish(type, content, contact, picIds) }
    }
}