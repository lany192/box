package cn.smallplants.client.provider;


import com.alibaba.android.arouter.facade.template.IProvider;

public interface HomeProvider extends IProvider {
    /**
     * 个人主页
     */
    void startHome(long userId);

    /**
     * 粉丝列表/关注的人列表
     */
    void startAttention(long userId, boolean fans);

    /**
     * 个人发表
     */
    void startPublish(long userId);
}
