package cn.smallplants.client.model.entity

import cn.smallplants.client.model.response.DiaryItem
import cn.smallplants.client.model.response.PageInfo
import java.lang.Deprecated

@Deprecated
class PlantDetailData : PageInfo<DiaryItem>() {
    var detail: PlantDetail? = null
}