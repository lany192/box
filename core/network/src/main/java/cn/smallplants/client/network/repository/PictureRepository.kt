package cn.smallplants.client.network.repository

import cn.smallplants.client.model.entity.Picture
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import com.github.lany192.utils.JsonUtils
import kotlinx.coroutines.flow.Flow

class PictureRepository(private val service: ApiService) : BaseRepository() {
    /**
     * 上传图片
     */
    fun uploadPicture(images: List<Picture>): Flow<ApiResult<List<Long>>> {
        return request { service.uploadPicture(JsonUtils.object2json(images)) }
    }
}