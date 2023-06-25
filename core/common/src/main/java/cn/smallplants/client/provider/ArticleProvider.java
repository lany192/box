package cn.smallplants.client.provider;


import com.alibaba.android.arouter.facade.template.IProvider;

public interface ArticleProvider extends IProvider {

    void startArticleDetail(long pid, long cid, long rid);

    void startArticleCreate();
}
