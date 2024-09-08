package com.github.lany192.dialog

import android.text.TextUtils
import android.text.method.MovementMethod
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.view.View
import androidx.annotation.ColorRes
import com.github.lany192.dialog.databinding.DialogSimpleBinding
import com.github.lany192.interfaces.OnSimpleListener
import java.lang.reflect.Modifier
import kotlin.math.abs

// java ----------------------------------------
//    SimpleDialog dialog = new SimpleDialog();
//    dialog.setTitle("提示");
//    dialog.setMessage("猜猜我是谁");
//    dialog.setRightBtn("确定", new OnSimpleListener() {
//        @Override
//        public void onCallback() {
//
//        }
//    });
//    dialog.setLeftBtn("取消", new OnSimpleListener() {
//        @Override
//        public void onCallback() {
//
//        }
//    });
//    dialog.show();
//
// kotlin ----------------------------------------
//SimpleDialog().apply {
//    title = "我是标题"
//    message = "猜猜我是谁"
//    leftButton = "取消"
//    rightButton = "确定"
//    rightClickListener = OnSimpleListener {
//
//    }
//    leftClickListener = OnSimpleListener {
//
//    }
//}.show()
class SimpleDialog : BaseDialog<DialogSimpleBinding>() {
    @ColorRes
    var titleColor = R.color.text_1level
    var titleSize = 16f
    var title: CharSequence? = null

    @ColorRes
    var messageColor = R.color.text_3level
    var message: CharSequence? = null
    var messageSize = 14f
    var movementMethod: MovementMethod? = null

    @ColorRes
    var rightTextColor = 0
    var rightButton: CharSequence? = null
    var rightClickListener: OnSimpleListener? = null

    @ColorRes
    var leftTextColor = 0
    var leftButton: CharSequence? = null
    var leftClickListener: OnSimpleListener? = null

    override fun init() {
        if (TextUtils.isEmpty(title)) {
            binding.title.visibility = View.GONE
        } else {
            binding.title.text = title
            binding.title.textSize = titleSize
            binding.title.visibility = View.VISIBLE
            binding.title.setTextColorId(titleColor)
        }
        if (!TextUtils.isEmpty(message)) {
            binding.content.setTextColorId(messageColor)
            binding.content.text = message
            binding.content.post {
                if (binding.content.lineCount > 1) {
                    binding.content.gravity = Gravity.LEFT
                } else {
                    binding.content.gravity = Gravity.CENTER
                }
            }
            binding.content.textSize = messageSize
            binding.content.setTextColorId(R.color.text_2level)
            if (movementMethod != null) {
                binding.content.movementMethod = movementMethod
            } else {
                binding.content.movementMethod = ScrollingMovementMethod.getInstance()
            }
        }

        if (!TextUtils.isEmpty(rightButton)) {
            binding.rightButton.text = rightButton
            binding.rightButton.visibility = View.VISIBLE
            if (rightTextColor != 0) {
                binding.rightButton.setTextColor(getColor(rightTextColor))
            }
            binding.rightButton.setOnClickListener {
                cancel()
                rightClickListener?.onCallback()
            }
        } else {
            binding.rightButton.visibility = View.GONE
        }
        var isShowDivider = false
        if (!TextUtils.isEmpty(leftButton)) {
            isShowDivider = true
            binding.leftButton.text = leftButton
            binding.leftButton.visibility = View.VISIBLE
            if (leftTextColor != 0) {
                binding.leftButton.setTextColor(getColor(leftTextColor))
            }
            binding.leftButton.setOnClickListener {
                cancel()
                leftClickListener?.onCallback()
            }
        } else {
            binding.leftButton.visibility = View.GONE
        }
        if (isShowDivider) {
            binding.divider.visibility = View.VISIBLE
        } else {
            binding.divider.visibility = View.GONE
        }
    }

    /**
     * 开启单例模式
     */
    fun setSingle() {
        setId(uniqueDialogId())
    }

    /**
     * 根据调用类名和执行代码位置获取一个唯一的id
     */
    private fun uniqueDialogId(): Long {
        var dialogId = System.currentTimeMillis()
        val stackTraces = Throwable().stackTrace
        for (stackTrace in stackTraces) {
            val lineNumber = stackTrace.lineNumber
            if (lineNumber > 0) {
                val className = stackTrace.className
                try {
                    val clazz = Class.forName(className)
                    if (!filterStackTraceClass(clazz)) {
                        val hashCode = (stackTrace.fileName + ":" + lineNumber).hashCode()
                        dialogId = abs(hashCode.toDouble()).toLong()
                        log.i("对话框调用信息:当前class:$className,调用代码行数：$lineNumber")
                        break
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        log.i("生成对话框的id:$dialogId")
        return dialogId
    }

    private fun filterStackTraceClass(clazz: Class<*>): Boolean {
        return if (SimpleDialog::class.java == clazz) {
            true
        } else if (clazz.isInterface) {
            true
        } else {
            Modifier.isAbstract(clazz.modifiers)
        }
    }
}