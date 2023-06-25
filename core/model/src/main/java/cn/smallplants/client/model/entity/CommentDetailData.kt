package cn.smallplants.client.model.entity

import cn.smallplants.client.model.response.CommentDetail
import cn.smallplants.client.model.response.PageInfo
import cn.smallplants.client.model.response.ReplyItem
import java.lang.Deprecated

@Deprecated
class CommentDetailData : PageInfo<ReplyItem>() {
    var detail: CommentDetail? = null
}