package com.lany192.box.router.ui;


import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.github.lany192.log.XLog;

/**
 * 中转界面，不显示
 */
public class SchemeFilterActivity extends FragmentActivity {
    private final XLog log = XLog.tag(getClass().getSimpleName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        直接通过ARouter处理外部Uri
        Uri uri = getIntent().getData();
        String url = uri.toString();
        log.i("接收到的跳转数据:" + url);
//        OtherUtils.skipByUrl(this, url);
        finish();
    }
}
