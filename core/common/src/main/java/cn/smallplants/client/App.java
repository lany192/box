package cn.smallplants.client;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.smallplants.client.provider.ArticleProvider;
import cn.smallplants.client.provider.BrowserProvider;
import cn.smallplants.client.provider.CommentProvider;
import cn.smallplants.client.provider.ConfigProvider;
import cn.smallplants.client.provider.FeedbackProvider;
import cn.smallplants.client.provider.GoodsProvider;
import cn.smallplants.client.provider.HomeProvider;
import cn.smallplants.client.provider.ImageProvider;
import cn.smallplants.client.provider.IndexProvider;
import cn.smallplants.client.provider.LocationProvider;
import cn.smallplants.client.provider.LoginProvider;
import cn.smallplants.client.provider.MessageProvider;
import cn.smallplants.client.provider.MettleProvider;
import cn.smallplants.client.provider.MyProvider;
import cn.smallplants.client.provider.PlantProvider;
import cn.smallplants.client.provider.SearchProvider;
import cn.smallplants.client.provider.SettingsProvider;
import cn.smallplants.client.provider.UserProvider;

public class App {

    public static BrowserProvider getBrowser() {
        return ARouter.getInstance().navigation(BrowserProvider.class);
    }

    public static FeedbackProvider getFeedback() {
        return ARouter.getInstance().navigation(FeedbackProvider.class);
    }

    public static LoginProvider getLogin() {
        return ARouter.getInstance().navigation(LoginProvider.class);
    }

    public static PlantProvider getPlant() {
        return ARouter.getInstance().navigation(PlantProvider.class);
    }

    public static ArticleProvider getArticle() {
        return ARouter.getInstance().navigation(ArticleProvider.class);
    }

    public static MettleProvider getMettle() {
        return ARouter.getInstance().navigation(MettleProvider.class);
    }

    public static SearchProvider getSearch() {
        return ARouter.getInstance().navigation(SearchProvider.class);
    }

    public static CommentProvider getComment() {
        return ARouter.getInstance().navigation(CommentProvider.class);
    }

    public static UserProvider getUser() {
        return ARouter.getInstance().navigation(UserProvider.class);
    }

    public static GoodsProvider getGoods() {
        return ARouter.getInstance().navigation(GoodsProvider.class);
    }

    public static MessageProvider getMessage() {
        return ARouter.getInstance().navigation(MessageProvider.class);
    }

    public static ConfigProvider getConfig() {
        return ARouter.getInstance().navigation(ConfigProvider.class);
    }

    public static SettingsProvider getSettings() {
        return ARouter.getInstance().navigation(SettingsProvider.class);
    }

    public static ImageProvider getImage() {
        return ARouter.getInstance().navigation(ImageProvider.class);
    }

    public static HomeProvider getHome() {
        return ARouter.getInstance().navigation(HomeProvider.class);
    }

    public static LocationProvider getLocation() {
        return ARouter.getInstance().navigation(LocationProvider.class);
    }

    public static IndexProvider getIndex() {
        return ARouter.getInstance().navigation(IndexProvider.class);
    }

    public static MyProvider getMy() {
        return ARouter.getInstance().navigation(MyProvider.class);
    }
}
