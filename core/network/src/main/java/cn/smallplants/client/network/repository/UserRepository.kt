package cn.smallplants.client.network.repository

import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.response.UserDetail
import cn.smallplants.client.model.v4.MultiItem
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class UserRepository(private val service: ApiService) : BaseRepository() {

    fun bindPhone(smsCode: Int, phone: String): Flow<ApiResult<UserDetail>> {
        return request { service.editPhone("", phone, smsCode) }
    }

    fun updateSignature(signature: String): Flow<ApiResult<UserDetail>> {
        return request { service.updateSignature(signature) }
    }

    fun updateNickName(nickname: String): Flow<ApiResult<UserDetail>> {
        return request { service.updateNickName(nickname) }
    }

    fun updateBirthday(date: LocalDate): Flow<ApiResult<UserDetail>> {
        return request { service.updateBirthday(date.year, date.monthValue, date.dayOfMonth) }
    }

    fun updateLocation(pid: Long, cid: Long, aid: Long): Flow<ApiResult<UserDetail>> {
        return request { service.updateLocation(pid, cid, aid) }
    }

    fun updateSex(sex: Int): Flow<ApiResult<UserDetail>> {
        return request { service.updateSex(sex) }
    }

    fun getUserInfo(): Flow<ApiResult<UserDetail>> {
        return request { service.getUserInfo() }
    }

    fun updateAvatar(picId: Long): Flow<ApiResult<UserDetail>> {
        return request { service.updateAvatar(picId) }
    }

    /**
     * 我发布的
     */
    fun publishList(
        //排序类型，0发布日期，1最近更新，2最多点赞
        sort: Int,
        //功能类型：0：植物，1：长文，2：晒图
        type: Int,
        //第几页
        pageNum: Int,
    ): Flow<ApiResult<PageInfo<MultiItem>>> {
        return request { service.publishList(sort, type, pageNum, 20) }
    }
}