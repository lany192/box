package cn.smallplants.client.network.repository

import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.v4.ArticleDetail
import cn.smallplants.client.model.v4.ArticleItem
import cn.smallplants.client.network.api.ApiService
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.arch.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class ArticleRepository(private val service: ApiService) : BaseRepository() {

    fun articleBuild(
        //文章标题
        title: String,
        //文章内容
        content: String,
        //封面图片ID
        coverId: String,
        //是否发布
        online: Boolean,
        //要关联的植记ids
        plantIds: List<Long>,
        //要关联的商品ids
        goodsIds: List<Long>,
    ): Flow<ApiResult<Long>> {
        return request { service.articleBuild(title, content, coverId, online, plantIds, goodsIds) }
    }

    fun articlePublish(articleId: Long): Flow<ApiResult<Any>> {
        return request { service.articlePublish(articleId) }
    }

    fun articleEdit(
        //文章标题
        articleId: Long,
        //文章标题
        title: String,
        //文章内容
        content: String,
        //封面图片ID
        coverId: String,
        //是否发布
        online: Boolean,
        //要关联的植记ids
        plantIds: List<Long>,
        //要关联的商品ids
        goodsIds: List<Long>
    ): Flow<ApiResult<Long>> {
        return request {
            service.articleEdit(articleId, title, content, coverId, online, plantIds, goodsIds)
        }
    }

    fun articleDetail(articleId: Long): Flow<ApiResult<ArticleDetail>> {
        return request {
            service.articleDetail(articleId)
        }
    }

    fun articleDelete(articleId: Long): Flow<ApiResult<Any>> {
        return request { service.articleDelete(articleId) }
    }

    fun articleDrafts(page: Int): Flow<ApiResult<PageInfo<ArticleItem>>> {
        return request { service.articleDrafts(page, 20) }
    }
}