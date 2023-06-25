package cn.smallplants.client.model.types;


public enum LoginType {
    /**
     * 未知
     */
    UNKNOWN("未知"),
    PASSWORD("手机登录"),
    PHONE_SMS("短信验证码"),
    ONE_KEY("一键登录"),
    QQ("QQ登录"),
    WECHAT("微信登录"),
    SINA("微博登录"),
    APPLE("苹果登录"),
    ALIPAY("支付宝登录"),
    REGISTER("注册密码"),
    FORGET("忘记密码");

    private final String title;

    LoginType(String title) {
        this.title = title;
    }

    public static LoginType get(int ordinal) {
        for (LoginType item : values()) {
            if (item.ordinal() == ordinal) {
                return item;
            }
        }
        return UNKNOWN;
    }

    public String getTitle() {
        return title;
    }
}
