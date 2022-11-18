package com.lany192.box.sample.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.github.lany192.utils.LogUtils;
import com.github.lany192.utils.XLog;

/**
 * 跳转过程中进行登录检查，需要登录的先登录再跳转
 *
 * @author lyg
 */
@Interceptor(priority = 1, name = "登录拦截器")
public class LoginInterceptor implements IInterceptor {
    private final XLog log = LogUtils.tag(getClass().getSimpleName());

    @Override
    public void init(Context context) {

    }

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
//        if (AccountModel.get().isLogin()) {
//            log.i("登录拦截:已经登录不拦截，直接通过" + postcard);
//            callback.onContinue(postcard);
//        } else {
//            if (paths.contains(postcard.getPath())) {
//                log.i("登录拦截:" + postcard.getPath() + "路由中断，该path需要登录但未登录，重定向到登录界面");
//                callback.onInterrupt(null);
//                String path = postcard.getPath();
//                Bundle bundle = postcard.getExtras();
//                bundle.putString(RoutePath.KEY_ROUTE_PATH, path);
//                SampleRouter.get().skip(RoutePath.APP_LOGIN, bundle);
//            } else {
//                log.i("登录拦截:不在拦截名单，不拦截，直接通过" + postcard);
//                callback.onContinue(postcard);
//            }
//        }

        callback.onContinue(postcard);
    }
}
