package cn.smallplants.client.network.interceptor;

import androidx.annotation.NonNull;

import com.github.lany192.arch.utils.DeviceId;
import com.github.lany192.arch.utils.PhoneUtils;

import java.io.IOException;
import java.util.Map;

import cn.smallplants.client.App;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Content-Type", "application/json;charset=UTF-8");
        builder.addHeader("client", "android");
        builder.addHeader("user-agent", PhoneUtils.getBaseInfo());
        builder.addHeader("deviceId", DeviceId.get().getDeviceId());
        builder.addHeader("version", String.valueOf(App.getConfig().getVersionCode()));

        builder.addHeader("token", App.getUser().getToken());
        builder.addHeader("uid", String.valueOf(App.getUser().getUserId()));

        Map<String, String> headers = App.getConfig().getHeaders();
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), "" + entry.getValue());
            }
        }
        return chain.proceed(builder.build());
    }
}
