package cn.smallplants.client.model.types;

/**
 * 短信类型
 */
public enum SmsType {
    /**
     *
     */
    UNKNOWN("未知"),
    REGISTER("注册"),
    FORGET("忘记密码"),
    BIND_PHONE("绑定手机"),
    BIND_ALIPAY("绑定支付宝"),
    WITHDRAW("提现"),
    ADMIN_LOGIN("管理员登录"),
    ADMIN_VERIFY("权限确认"),
    LOGIN("验证码登录");

    private final String title;

    SmsType(String title) {
        this.title = title;
    }

    public static SmsType get(int ordinal) {
        for (SmsType item : values()) {
            if (item.ordinal() == ordinal) {
                return item;
            }
        }
        return UNKNOWN;
    }

    public static SmsType get(String title) {
        for (SmsType item : values()) {
            if (item.getTitle().equals(title)) {
                return item;
            }
        }
        return UNKNOWN;
    }

    public String getTitle() {
        return title;
    }
}