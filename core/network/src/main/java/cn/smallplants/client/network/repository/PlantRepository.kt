package cn.smallplants.client.network.repository

import cn.smallplants.client.model.entity.EditPlantInfo
import cn.smallplants.client.model.entity.Picture
import cn.smallplants.client.model.response.DiaryDetail
import cn.smallplants.client.model.response.GoodsItem
import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.v4.PlantDetailListPage
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class PlantRepository(private val service: ApiService) : BaseRepository() {
    /**
     * 创建成长记
     */
    fun plantBuild(
        //植物标题
        title: String,
        //植物描述
        description: String,
        //要关联的图片ids
        picIds: List<Long>
    ): Flow<ApiResult<Long>> {
        return request { service.plantBuild(title, description, picIds) }
    }

    /**
     * 编辑植物
     */
    fun plantEdit(
        //成长记ID
        plantId: Long,
        //成长记标题
        title: String,
        //成长记描述
        description: String
    ): Flow<ApiResult<EditPlantInfo>> {
        return request { service.plantEdit(plantId, title, description) }
    }

    /**
     * 植物详情
     */
    fun plantDetail(
        //成长记ID
        id: Long,
        //第几页
        pageNum: Int
    ): Flow<ApiResult<PlantDetailListPage>> {
        return request { service.plantDetail(id, pageNum, 20) }
    }

    /**
     * 删除某植物
     */
    fun plantDelete(
        plantId: Long
    ): Flow<ApiResult<Any>> {
        return request { service.plantDelete(plantId) }
    }

    /**
     * 创建日记
     */
    fun diaryBuild(
        //植物ID
        plantId: Long,
        //日记时间，时间戳
        recordTime: Long,
        //植物描述
        description: String,
        //是否重置植物封面
        resetCover: Boolean,
        //要关联的图片ids
        picIds: List<Long>,
        //需要绑定的商品ids
        goodsIds: List<Long>
    ): Flow<ApiResult<Long>> {
        return request {
            service.diaryBuild(plantId, recordTime, description, resetCover, picIds, goodsIds)
        }
    }

    /**
     * 编辑日记
     */
    fun diaryEdit(
        //植物ID
        plantId: Long,
        //日记id
        diaryId: Long,
        //植物描述
        description: String,
        //日记时间，时间戳
        recordTime: Long,
        //是否重置植物封面
        resetCover: Boolean,
        //要关联的图片ids
        picIds: List<Long>,
        //没有被删除的图片id集
        oldImageIds: List<Long>,
        //需要绑定的商品ids
        goodsIds: List<Long>
    ): Flow<ApiResult<Long>> {
        return request {
            service.diaryEdit(
                plantId, diaryId, description, recordTime, resetCover, picIds, oldImageIds, goodsIds
            )
        }
    }

    /**
     * 日记信息详情
     */
    fun diaryDetail(id: Long): Flow<ApiResult<DiaryDetail>> {
        return request { service.diaryDetail(id) }
    }

    /**
     * 创建日记商品
     */
    fun goodsBuild(
        //商品描述
        title: String,
        //商品淘口令
        code: String
    ): Flow<ApiResult<Any>> {
        return request { service.goodsBuild(title, code) }
    }

    /**
     * 删除商品
     */
    fun goodsDelete(id: Long): Flow<ApiResult<Any>> {
        return request { service.goodsDelete(id) }
    }

    /**
     * 日记商品列表
     */
    fun goodsList(page: Int): Flow<ApiResult<PageInfo<GoodsItem>>> {
        return request { service.goodsList(page, 20) }
    }

    /**
     * 植物的所有图片
     */
    fun getPlantImages(plantId: Long): Flow<ApiResult<List<Picture>>> {
        return request { service.getPlantImages(plantId) }
    }
}