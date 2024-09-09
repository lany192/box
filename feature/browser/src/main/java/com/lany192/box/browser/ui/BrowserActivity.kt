package com.lany192.box.browser.ui

import android.os.Bundle
import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewBindingActivity
import com.github.lany192.arch.utils.BarUtils
import com.github.lany192.extension.log
import com.lany192.box.browser.databinding.ActivityBrowserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/browser/main")
class BrowserActivity : ViewBindingActivity<ActivityBrowserBinding>() {
    @Autowired(name = "title", desc = "标题")
    lateinit var title: String

    @Autowired(name = "url", desc = "链接")
    lateinit var url: String

    override fun initImmersionBar() {
        BarUtils.init(this).keyboardEnable(true).titleBar(binding.toolbar).init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(title)
        binding.webview.loadUrl(url)
        log("标题:$title,链接:$url")
    }

    /**
     * javascript回调
     *
     * @param callback 回调方法
     * @param value    回调值
     */
    private fun callback(callback: String, value: String) {
        if (!TextUtils.isEmpty(callback) && !TextUtils.isEmpty(callback.trim { it <= ' ' })) {
            binding.webview.loadUrl("javascript:$callback('$value')")
        }
    }
}