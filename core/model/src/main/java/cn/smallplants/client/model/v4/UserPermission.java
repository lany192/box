package cn.smallplants.client.model.v4;

import com.google.gson.annotations.SerializedName;

public class UserPermission {
    //是否可以下载某个植物的所有图片，用于控制是否显示下载入口
    @SerializedName("downloadPlantImagesEnable")
    private boolean downloadPlantImagesEnable;

    public boolean isDownloadPlantImagesEnable() {
        return downloadPlantImagesEnable;
    }

    public void setDownloadPlantImagesEnable(boolean downloadPlantImagesEnable) {
        this.downloadPlantImagesEnable = downloadPlantImagesEnable;
    }
}
