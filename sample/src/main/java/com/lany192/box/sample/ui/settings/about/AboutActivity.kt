package com.lany192.box.sample.ui.settings.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.alibaba.android.arouter.SampleRouter
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.BoxActivity
import com.lany192.box.sample.databinding.ActivityAboutBinding
import dagger.hilt.android.AndroidEntryPoint
import de.psdev.licensesdialog.LicensesDialog
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20
import de.psdev.licensesdialog.licenses.MITLicense
import de.psdev.licensesdialog.model.Notice
import de.psdev.licensesdialog.model.Notices


@AndroidEntryPoint
@Route(path = "/ui/about")
class AboutActivity : BoxActivity<AboutViewModel, ActivityAboutBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.licenceView.setOnClickListener { showLicensesDialog() }
        binding.marketView.setOnClickListener { gotoMarket() }
        binding.privacyView.setOnClickListener {
            SampleRouter.startBrowser("隐私政策", "https://www.baidu.com")
        }
        binding.protocolView.setOnClickListener {
            SampleRouter.startBrowser("用户协议", "https://www.baidu.com")
        }
        binding.feedbackView.setOnClickListener {
            SampleRouter.startFeedback()
        }
    }

    private fun showLicensesDialog() {
        val notices = Notices()
        notices.addNotice(
            Notice(
                "xLog",
                "https://github.com/elvishew/xLog",
                "Copyright 2015-2021 Elvis Hew",
                ApacheSoftwareLicense20()
            )
        )
        notices.addNotice(
            Notice(
                "ToastUtils",
                "https://github.com/getActivity/ToastUtils",
                "Copyright 2018 Huang JinQun",
                ApacheSoftwareLicense20()
            )
        )
        notices.addNotice(
            Notice(
                "BaseRecyclerViewAdapterHelper",
                "https://github.com/CymChad/BaseRecyclerViewAdapterHelper",
                "Copyright (c) 2019 陈宇明",
                MITLicense()
            )
        )
        LicensesDialog.Builder(this)
            .setNotices(notices)
            .setIncludeOwnLicense(true)
            .build()
            .show()
    }

    /**
     * 客服QQ
     */
    private fun gotoQQ() {
        val uri = Uri.parse("market://details?id=$packageName")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent.setPackage("com.tencent.android.qqdownloader")
        startActivity(intent)
    }

    /**
     * 给个好评
     */
    private fun gotoMarket() {
        val uri = Uri.parse("market://details?id=$packageName")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent.setPackage("com.tencent.android.qqdownloader")
        startActivity(intent)
    }
}