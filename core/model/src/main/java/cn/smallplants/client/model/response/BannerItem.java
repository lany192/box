package cn.smallplants.client.model.response;


import java.io.Serializable;

import cn.smallplants.client.model.entity.Picture;

/**
 * @author lany
 */

public class BannerItem implements Serializable {
    Picture cover;
    private String title;

    public BannerItem(Picture cover, String title) {
        this.cover = cover;
        this.title = title;
    }
}
