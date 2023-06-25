package cn.smallplants.client.model.v4;

import com.google.gson.annotations.SerializedName;

import cn.smallplants.client.model.response.DiaryItem;
import cn.smallplants.client.model.response.PageInfo;
import cn.smallplants.client.model.response.PlantDetail;

public class PlantDetailListPage extends PageInfo<DiaryItem> {
    //详情信息
    @SerializedName("detail")
    PlantDetail detail;

    public PlantDetail getDetail() {
        return detail;
    }

    public void setDetail(PlantDetail detail) {
        this.detail = detail;
    }
}
