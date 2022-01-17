package com.github.lany192.update.dialog;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.github.lany192.dialog.NormalDialog;
import com.github.lany192.update.R;
import com.github.lany192.update.config.UpdateConfig;
import com.github.lany192.update.databinding.DialogAppUpdateBinding;
import com.github.lany192.update.listener.OnDownloadListener;
import com.github.lany192.update.manager.UpdateManager;
import com.github.lany192.update.service.DownloadService;
import com.github.lany192.update.utils.ApkUtil;
import com.github.lany192.update.utils.Constant;

import java.io.File;

public class AppUpdateDialog extends NormalDialog<DialogAppUpdateBinding> implements OnDownloadListener {
    private final int install = 0x45F;
    private boolean forcedUpgrade;
    private File apk;

    @Override
    protected void init() {
        UpdateConfig configuration = UpdateManager.getInstance().getConfiguration();
        configuration.setOnDownloadListener(this);
        forcedUpgrade = configuration.isForcedUpgrade();
        binding.progressBar.setVisibility(forcedUpgrade ? View.VISIBLE : View.GONE);
        binding.update.setTag(0);

        //强制升级
        if (forcedUpgrade) {
            binding.cancel.setVisibility(View.GONE);
            setCanceledOnTouchOutside(false);
            setCancelable(false);
        }
        //设置界面数据
        if (!TextUtils.isEmpty(UpdateManager.getInstance().getApkVersionName())) {
            String newVersion = getString(R.string.dialog_new);
            binding.title.setText(String.format(newVersion, UpdateManager.getInstance().getApkVersionName()));
        }
        if (!TextUtils.isEmpty(UpdateManager.getInstance().getApkSize())) {
            String newVersionSize = getString(R.string.dialog_new_size);
            binding.size.setText(String.format(newVersionSize, UpdateManager.getInstance().getApkSize()));
            binding.size.setVisibility(View.VISIBLE);
        }
        binding.description.setText(UpdateManager.getInstance().getApkDescription());

        binding.cancel.setOnClickListener(view -> cancel());
        binding.update.setOnClickListener(view -> {
            if ((int) binding.update.getTag() == install) {
                installApk();
                return;
            }
            if (forcedUpgrade) {
                binding.update.setEnabled(false);
                binding.update.setText(R.string.background_downloading);
            } else {
                dismiss();
            }
            getContext().startService(new Intent(getContext(), DownloadService.class));
        });
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
        if (max != -1 && binding.progressBar.getVisibility() == View.VISIBLE) {
            int curr = (int) (progress / (double) max * 100.0);
            binding.progressBar.setProgress(curr);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void done(File apk) {
        this.apk = apk;
        if (forcedUpgrade) {
            binding.update.setTag(install);
            binding.update.setEnabled(true);
            binding.update.setText(R.string.click_hint);
        }
    }

    @Override
    public void error(Exception e) {

    }
}
