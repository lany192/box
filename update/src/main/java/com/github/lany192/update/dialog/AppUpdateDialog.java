package com.github.lany192.update.dialog;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.lany192.dialog.BasicDialog;
import com.github.lany192.update.R;
import com.github.lany192.update.config.UpdateConfig;
import com.github.lany192.update.listener.OnDownloadListener;
import com.github.lany192.update.manager.UpdateManager;
import com.github.lany192.update.service.DownloadService;
import com.github.lany192.update.utils.ApkUtil;
import com.github.lany192.update.utils.Constant;

import java.io.File;

public class AppUpdateDialog extends BasicDialog implements View.OnClickListener, OnDownloadListener {
    private final int install = 0x45F;
    private boolean forcedUpgrade;
    private Button update;
    private ProgressBar progressBar;
    private File apk;

    @Override
    protected boolean bottomStyle() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_app_update;
    }

    @Override
    protected void init() {
        UpdateConfig configuration = UpdateManager.getInstance().getConfiguration();
        configuration.setOnDownloadListener(this);
        forcedUpgrade = configuration.isForcedUpgrade();
        View ibClose = findViewById(R.id.btn_cancel);
        TextView title = findViewById(R.id.tv_title);
        TextView size = findViewById(R.id.tv_size);
        TextView description = findViewById(R.id.tv_description);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(forcedUpgrade ? View.VISIBLE : View.GONE);
        update = findViewById(R.id.btn_update);
        update.setTag(0);
        update.setOnClickListener(this);
        ibClose.setOnClickListener(this);

        //强制升级
        if (forcedUpgrade) {
            ibClose.setVisibility(View.GONE);
            setCanceledOnTouchOutside(false);
            setCancelable(false);
        }
        //设置界面数据
        if (!TextUtils.isEmpty(UpdateManager.getInstance().getApkVersionName())) {
            String newVersion = getString(R.string.dialog_new);
            title.setText(String.format(newVersion, UpdateManager.getInstance().getApkVersionName()));
        }
        if (!TextUtils.isEmpty(UpdateManager.getInstance().getApkSize())) {
            String newVersionSize = getString(R.string.dialog_new_size);
            size.setText(String.format(newVersionSize, UpdateManager.getInstance().getApkSize()));
            size.setVisibility(View.VISIBLE);
        }
        description.setText(UpdateManager.getInstance().getApkDescription());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_cancel) {
            if (!forcedUpgrade) {
                dismiss();
            }
        } else if (id == R.id.btn_update) {
            if ((int) update.getTag() == install) {
                installApk();
                return;
            }
            if (forcedUpgrade) {
                update.setEnabled(false);
                update.setText(R.string.background_downloading);
            } else {
                dismiss();
            }
            getContext().startService(new Intent(getContext(), DownloadService.class));
        }
    }

    /**
     * 强制更新，点击进行安装
     */
    private void installApk() {
        ApkUtil.installApk(getContext(), Constant.AUTHORITIES, apk);
    }

    @Override
    public void start() {

    }

    @Override
    public void downloading(int max, int progress) {
        if (max != -1 && progressBar.getVisibility() == View.VISIBLE) {
            int curr = (int) (progress / (double) max * 100.0);
            progressBar.setProgress(curr);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void done(File apk) {
        this.apk = apk;
        if (forcedUpgrade) {
            update.setTag(install);
            update.setEnabled(true);
            update.setText(R.string.click_hint);
        }
    }

    @Override
    public void error(Exception e) {

    }
}
