package cn.smallplants.client.model.entity

import cn.smallplants.client.model.response.Share
import cn.smallplants.client.model.response.UserItem
import java.lang.Deprecated

@Deprecated
class PlantDetail {
    var plantId = 0
    var createTime: Long = 0
    var title: String? = null
    var description: String? = null
    var recommend = false
    var views = 0
    var location: String? = null
    var author: UserItem? = null
    var likeCount = 0
    var diaryCount = 0
    var cover: Image? = null
    var images: List<Image>? = null
    var like = false
    var share: Share? = null
    var commentCount: Long = 0
}