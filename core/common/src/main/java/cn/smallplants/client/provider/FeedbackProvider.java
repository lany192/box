package cn.smallplants.client.provider;


import com.alibaba.android.arouter.facade.template.IProvider;

public interface FeedbackProvider extends IProvider {

    void startFeedback();

    void startFeedbackRecords();

    void startReport(int type, long id);

    void startReportRecords();
}
