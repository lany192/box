package com.lany192.box.user.ui.signature

import android.os.Bundle
import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewModelActivity
import com.github.lany192.arch.interfaces.SimpleTextWatcher
import com.github.lany192.arch.utils.BarUtils
import com.github.lany192.extension.toast
import com.github.lany192.link.Link
import com.github.lany192.link.LinkBuilder
import com.github.lany192.utils.KeyboardWatcher
import com.lany192.box.user.R
import com.lany192.box.user.UserHelper
import com.lany192.box.user.databinding.ActivitySignatureBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
@Route(path = "/user/signature", name = "修改个性签名")
class SignatureActivity : ViewModelActivity<SignatureViewModel, ActivitySignatureBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.right.setOnClickListener { viewModel.submit() }
        binding.input.addTextChangedListener(object : SimpleTextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                showCount(s.length)
                viewModel.signature = s.toString().trim()
            }
        })
        val signature = UserHelper.getInstance().signature
        if (!TextUtils.isEmpty(signature)) {
            binding.input.setText(signature)
            binding.input.setSelection(signature.length)
        }
        KeyboardWatcher(this, object : KeyboardWatcher.OnKeyboardListener {

                override fun onChanged(showKeyboard: Boolean, keyboardHeight: Int) {
                    toast("键盘弹出:$showKeyboard,高度：$keyboardHeight")
                }
            })
    }

    /**
     * 显示已经输入个数
     */
    private fun showCount(count: Int) {
        var color = getColorResId(R.color.text_4level)
        if (count == 60) {
            color = getColorResId(R.color.red)
        }
        binding.count.text = LinkBuilder
            .from(this, String.format(Locale.getDefault(), "%d/60", count))
            .addLink(Link(count.toString()).setTextColor(color).setUnderlined(false))
            .setFindOnlyFirstMatchesForAnyLink(true)
            .build()
    }
}