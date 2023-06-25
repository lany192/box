package cn.smallplants.client.model.response;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import cn.smallplants.client.model.entity.Place;

//用户信息详情
public class UserDetail implements Serializable {
    //所在地区
    Place place;
    //账上可用余额
    BigDecimal balance;
    //账上被冻结余额
    BigDecimal freezeBalance;
    //性别，0未知1男2女
    int sex;
    //用户等级
    Level level;
    //用户id
    private long uid;
    //    //姓名
//private    String username;
    //昵称
    private String nickname;
    //头像
    private String avatar;
    //签名
    private String signature;
    //邮箱地址
    private String email;
    //手机号码
    private String phone;
    //QQ号码
    private String qq;
    //是否绑定手机
    private boolean bindPhone;
    //是否绑定微信
    private boolean bindWeChat;
    //是否绑定支付宝
    private boolean bindAlipay;
    //是否有密码
    private boolean havePwd;
    //我的积分总量
    private long centCount;
    //我的粉丝数
    private long fansCount;
    //我关注的人数
    private long followCount;
    //我获得的点赞总数数，获赞总数，包含植物、评论、回复等
    private long likeCount;
    //我的发布植物数量
    private long publishPlantCount;
    //我喜欢的植物数量
    private long likePlantCount;
    //我的发布长文数量
    private long longTextCount;
    //我的发布晒图数量
    private long lifeRecordCount;
    //邀请码
    private String inviteCode;
    //是否已经设置支付密码
    private boolean hasPayPwd;
    //是否实名制
    private boolean realName;
    //二维码显示内容
    private String qrcode;
    //用户标签
    private List<String> labels;
    //是否VIP用户
    private boolean vip;
    //生日年月日
    private String birthday;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public boolean isBindPhone() {
        return bindPhone;
    }

    public void setBindPhone(boolean bindPhone) {
        this.bindPhone = bindPhone;
    }

    public boolean isBindWeChat() {
        return bindWeChat;
    }

    public void setBindWeChat(boolean bindWeChat) {
        this.bindWeChat = bindWeChat;
    }

    public boolean isBindAlipay() {
        return bindAlipay;
    }

    public void setBindAlipay(boolean bindAlipay) {
        this.bindAlipay = bindAlipay;
    }

    public boolean isHavePwd() {
        return havePwd;
    }

    public void setHavePwd(boolean havePwd) {
        this.havePwd = havePwd;
    }

    public long getCentCount() {
        return centCount;
    }

    public void setCentCount(long centCount) {
        this.centCount = centCount;
    }

    public long getFansCount() {
        return fansCount;
    }

    public void setFansCount(long fansCount) {
        this.fansCount = fansCount;
    }

    public long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(long followCount) {
        this.followCount = followCount;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getPublishPlantCount() {
        return publishPlantCount;
    }

    public void setPublishPlantCount(long publishPlantCount) {
        this.publishPlantCount = publishPlantCount;
    }

    public long getLikePlantCount() {
        return likePlantCount;
    }

    public void setLikePlantCount(long likePlantCount) {
        this.likePlantCount = likePlantCount;
    }

    public long getLongTextCount() {
        return longTextCount;
    }

    public void setLongTextCount(long longTextCount) {
        this.longTextCount = longTextCount;
    }

    public long getLifeRecordCount() {
        return lifeRecordCount;
    }

    public void setLifeRecordCount(long lifeRecordCount) {
        this.lifeRecordCount = lifeRecordCount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(BigDecimal freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public boolean isHasPayPwd() {
        return hasPayPwd;
    }

    public void setHasPayPwd(boolean hasPayPwd) {
        this.hasPayPwd = hasPayPwd;
    }

    public boolean isRealName() {
        return realName;
    }

    public void setRealName(boolean realName) {
        this.realName = realName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
