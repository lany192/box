package com.github.lany192.box.debug;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.github.lany192.box.R;
import com.github.lany192.box.activity.BaseActivity;
import com.github.lany192.box.config.ActivityConfig;
import com.github.lany192.box.utils.ClipboardUtils;
import com.github.lany192.box.utils.FileUtils;
import com.github.lany192.box.utils.PhoneUtils;
import com.hjq.toast.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DebugActivity extends BaseActivity {
    private TextView mInfoText;
    private String mErrorInfo;
    private Calendar mCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @NonNull
    @Override
    protected ActivityConfig getConfig(ActivityConfig config) {
        return config.layoutId(R.layout.activity_debug);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mCalendar = Calendar.getInstance();
        setBarTitle("日志" + sdf.format(mCalendar.getTime()));
        mInfoText = findViewById(R.id.debug_info_view);
        findViewById(R.id.debug_date_btn).setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(self, DatePickerDialog.THEME_HOLO_LIGHT, (view, year, monthOfYear, dayOfMonth) -> {
                mCalendar.set(year, monthOfYear, dayOfMonth);
                log.i("选择结果: " + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日 ");

                setBarTitle("日志" + sdf.format(mCalendar.getTime()));
                showLog(mCalendar.getTime());
            }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DATE));
            dialog.show();
        });
        findViewById(R.id.debug_copy_btn).setOnClickListener(v -> {
            ClipboardUtils.setText(self, mErrorInfo);
            ToastUtils.show("复制成功！");
        });

        showLog(mCalendar.getTime());
    }

    private void showLog(Date date) {
        String content = "基本信息:" + PhoneUtils.getBaseInfo() + ";" + PhoneUtils.getAppVersionName();
        String log = FileUtils.getLogPathByDate(this, date);
        mErrorInfo = content + "\n\n" + log;
        mInfoText.setText(mErrorInfo);
    }
}
