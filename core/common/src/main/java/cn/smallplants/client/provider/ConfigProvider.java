package cn.smallplants.client.provider;


import com.alibaba.android.arouter.facade.template.IProvider;

import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;

public interface ConfigProvider extends IProvider {

    String getAppId();

    /**
     * 是否正式包
     */
    boolean isRelease();

    /**
     * 是否内部包
     */
    boolean isInternal();

    /**
     * 非正式包
     */
    boolean isDev();

    HashMap<String, String> getHeaders();

    String getBaseUrl();

    long getConnectTimeout();

    long getReadTimeout();

    long getWriteTimeout();

    List<Interceptor> getInterceptors();

    int getVersionCode();

    String getFileProvider();

    String getChannel();

    String getGitCommitId();
}
