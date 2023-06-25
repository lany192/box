package cn.smallplants.client.model.types;


/**
 * 通用目标类型
 */
public enum TargetType {
    UNKNOWN("未知"),
    PLANT("成长记"),
    DIARY("日记"),
    PLANT_COMMENT("植物评论"),
    PLANT_REPLY("植物回复"),
    PLATE("板块"),

    GOODS("商品"),
    BRAND("品牌"),
    GOODS_COMMENT("商品评论"),
    GOODS_REPLY("商品回复"),
    GOODS_CATEGORY("商品分类"),

    MEMBER("会员"),
    CATEGORY("分类"),

    MESSAGE("消息"),
    FEEDBACK("反馈"),
    CENT("积分"),
    PRIZE("奖品");

    private final String title;

    TargetType(String title) {
        this.title = title;
    }

    public static TargetType get(int ordinal) {
        for (TargetType item : values()) {
            if (item.ordinal() == ordinal) {
                return item;
            }
        }
        return UNKNOWN;
    }

    public static TargetType get(String title) {
        for (TargetType item : values()) {
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
