package com.github.lany192.update.dialog;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.github.lany192.dialog.BaseDialog;
import com.github.lany192.update.R;
import com.github.lany192.update.config.UpdateConfig;
import com.github.lany192.update.databinding.DialogAppUpdateBinding;
import com.github.lany192.update.listener.OnDownloadListener;
import com.github.lany192.update.manager.UpdateManager;
import com.github.lany192.update.service.DownloadService;
import com.github.lany192.update.utils.ApkUtil;
import com.github.lany192.update.utils.Constant;

import java.io.File;

public class AppUpdateDialog extends BaseDialog<DialogAppUpdateBinding>
        implements View.OnClickListener, OnDownloadListener {
    private final int install = 0x45F;
    private boolean forcedUpgrade;
    private File apk;

    @Override
    protected boolean bottomStyle() {
        return true;
    }

    @Override
    protected void init() {
        UpdateConfig configuration = UpdateManager.getInstance().getConfiguration();
        configuration.setOnDownloadListener(this);
        forcedUpgrade = configuration.isForcedUpgrade();
        getBinding().progressBar.setVisibility(forcedUpgrade ? View.VISIBLE : View.GONE);
        getBinding().update.setTag(0);
        getBinding().update.setOnClickListener(this);
        getBinding().cancel.setOnClickListener(this);

        //强制升级
        if (forcedUpgrade) {
            getBinding().cancel.setVisibility(View.GONE);
            setCanceledOnTouchOutside(false);
            setCancelable(false);
        }
        //设置界面数据
        if (!TextUtils.isEmpty(UpdateManager.getInstance().getApkVersionName())) {
            String newVersion = getString(R.string.dialog_new);
            getBinding().title.setText(String.format(newVersion, UpdateManager.getInstance().getApkVersionName()));
        }
        if (!TextUtils.isEmpty(UpdateManager.getInstance().getApkSize())) {
            String newVersionSize = getString(R.string.dialog_new_size);
            getBinding().size.setText(String.format(newVersionSize, UpdateManager.getInstance().getApkSize()));
            getBinding().size.setVisibility(View.VISIBLE);
        }
        getBinding().description.setText(UpdateManager.getInstance().getApkDescription());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cancel) {
            if (!forcedUpgrade) {
                dismiss();
            }
        } else if (id == R.id.update) {
            if ((int) getBinding().update.getTag() == install) {
                installApk();
                return;
            }
            if (forcedUpgrade) {
                getBinding().update.setEnabled(false);
                getBinding().update.setText(R.string.background_downloading);
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
        if (max != -1 && getBinding().progressBar.getVisibility() == View.VISIBLE) {
            int curr = (int) (progress / (double) max * 100.0);
            getBinding().progressBar.setProgress(curr);
        } else {
            getBinding().progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void done(File apk) {
        this.apk = apk;
        if (forcedUpgrade) {
            getBinding().update.setTag(install);
            getBinding().update.setEnabled(true);
            getBinding().update.setText(R.string.click_hint);
        }
    }

    @Override
    public void error(Exception e) {

    }
}
