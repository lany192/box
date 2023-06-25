package cn.smallplants.client.model.response;

import java.io.Serializable;

//签到结果
public class SignInDetail implements Serializable {
    //签到日期
    String date;
    //连续签到天数
    private long dayCount;
    //奖励积分
    private int cent;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getDayCount() {
        return dayCount;
    }

    public void setDayCount(long dayCount) {
        this.dayCount = dayCount;
    }

    public int getCent() {
        return cent;
    }

    public void setCent(int cent) {
        this.cent = cent;
    }
}
