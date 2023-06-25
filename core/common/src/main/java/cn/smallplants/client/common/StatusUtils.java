package cn.smallplants.client.common;

import com.hjq.toast.ToastUtils;

import cn.smallplants.client.App;


public class StatusUtils {

    public static void otherStatus(int code, String msg) {
        switch (code) {
            case 401://未登录或者登录过期
                ToastUtils.show(msg);
                App.getUser().logout();
                break;
            case 400:
                ToastUtils.show(msg);
                break;
            default:
                ToastUtils.show(R.string.server_error);
                break;
        }
    }
}
