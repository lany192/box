package com.github.lany192.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.Window
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.lany192.log.XLog
import com.github.lany192.utils.ContextUtils
import com.github.lany192.utils.DensityUtils

/**
 * 对话框基类
 */
abstract class PriorityDialog : DialogFragment(), Comparable<PriorityDialog> {
    @JvmField
    protected val TAG: String = this.javaClass.name
    private val dismissListeners: MutableList<DialogInterface.OnDismissListener> = ArrayList()

    @JvmField
    protected var log: XLog = XLog.tag(TAG)
    private var canceledOnTouchOutside = true
    private var isInitLoaded = false
    private var flag = false

    /**
     * 优先级，数值越大优先级越高，优先级仅在队列中生效
     */
    var priority: Int = 0

    /**
     * 是否单例模式
     */
    var isSingle: Boolean = false

    /**
     * 对话框id
     */
    var dialogId: Long = this.javaClass.hashCode().toLong()

    protected abstract fun init()

    override fun compareTo(other: PriorityDialog): Int {
        //比较优先级
        return other.priority - priority
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        for (listener in dismissListeners) {
            listener.onDismiss(dialog)
        }
    }

    /**
     * 设置单例模式
     * 指定唯一不变的对话框id表示单例模式，多次调用只显示一次
     */
    fun setId(dialogId: Long) {
        this.dialogId = dialogId
        this.isSingle = true
    }

    /**
     * 对话框背景蒙层透明度
     */
    open fun getDimAmount(): Float {
        return 0.5f
    }

    /**
     * 添加隐藏事件监听
     */
    fun addOnDismissListener(listener: DialogInterface.OnDismissListener) {
        dismissListeners.add(listener)
    }

    open fun show() {
        DialogHelper.get().show(this)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (flag || isAdded || null != manager.findFragmentByTag(tag)) {
            log.d("已经显示，忽略......")
        } else {
            flag = true
            try {
                //super.show(manager, tag);

                val dismissed = DialogFragment::class.java.getDeclaredField("mDismissed")
                dismissed.isAccessible = true
                dismissed[this] = false

                val shown = DialogFragment::class.java.getDeclaredField("mShownByMe")
                shown.isAccessible = true
                shown[this] = true

                val ft = manager.beginTransaction()
                ft.add(this, tag)
                ft.commitAllowingStateLoss()
                if (isSingle) {
                    DialogHelper.get().addSingleDialogId(dialogId)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                log.e("对话框显示异常：" + e.message)
            }
            Handler().post { flag = false }
        }
    }

    fun show(context: Context) {
        val activity = ContextUtils.context2FragmentActivity(context)
        if (activity != null && !activity.isFinishing && !activity.isDestroyed) {
            show(activity.supportFragmentManager)
        } else {
            log.e("没有context，不能调起对话框")
        }
    }

    fun show(fragment: Fragment) {
        show(fragment.requireActivity())
    }

    private fun show(manager: FragmentManager) {
        show(manager, TAG + this)
    }

    fun cancel() {
        try {
            dismissNow()
        } catch (e: Exception) {
            e.printStackTrace()
            try {
                dismissAllowingStateLoss()
            } catch (e1: Exception) {
                e1.printStackTrace()
                val fragmentManager = fragmentManager
                if (fragmentManager != null) {
                    val transaction = fragmentManager.beginTransaction()
                    transaction.remove(this)
                    transaction.commitAllowingStateLoss()
                } else {
                    log.e("对话框context异常")
                }
            }
        }
    }

    fun getColor(@ColorRes colorResId: Int): Int {
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    fun getStringId(@StringRes resId: Int): String {
        return ContextUtils.getContext().getString(resId)
    }

    open fun getDialogHeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    open fun getDialogWidth(): Int {
        return if (bottomStyle()) {
            WindowManager.LayoutParams.MATCH_PARENT
        } else {
            DensityUtils.dp2px(300f)
        }
    }

    /**
     * 是否是底部弹窗样式
     */
    open fun bottomStyle(): Boolean {
        return false
    }

    override fun getTheme(): Int {
        if (bottomStyle()) {
            return R.style.BottomDialogTheme
        }
        return super.getTheme()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object : Dialog(requireActivity(), this.theme) {
            override fun onTouchEvent(event: MotionEvent): Boolean {
                if (canceledOnTouchOutside) {
                    val window = window
                    if (window != null) {
                        val decorView = window.decorView
                        val slop = ViewConfiguration.get(context).scaledWindowTouchSlop
                        //是否在对话框外点击
                        if ((event.x < -slop)
                            || (event.y < -slop)
                            || (event.x > (decorView.width + slop))
                            || (event.y > (decorView.height + slop))
                        ) {
                            onTouchDialogOutside()
                        }
                    }
                }
                return super.onTouchEvent(event)
            }

            @Deprecated("Deprecated in Java")
            override fun onBackPressed() {
                onBackKeyClicked()
                if (isCancelable) {
                    super.onBackPressed()
                }
            }
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(isCancelable)
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
        resetWindow(dialog)
        return dialog
    }

    @SuppressLint("WrongConstant")
    protected fun resetWindow(dialog: Dialog) {
        dialog.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setDimAmount(getDimAmount())
            if (bottomStyle()) {
                it.setGravity(Gravity.BOTTOM)
            } else {
                it.setGravity(Gravity.CENTER)
            }
            it.setLayout(getDialogWidth(), getDialogHeight())
        }
    }

    /**
     * 对话框外部点击触发点
     */
    fun onBackKeyClicked() {
        log.i("返回按键点击")
    }

    /**
     * 对话框外部点击触发点
     */
    fun onTouchDialogOutside() {
        log.i("对话框外部点击")
    }

    fun setCanceledOnTouchOutside(canceledOnTouchOutside: Boolean) {
        this.canceledOnTouchOutside = canceledOnTouchOutside
        val dialog = dialog
        dialog?.setCanceledOnTouchOutside(canceledOnTouchOutside)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        val dialog = dialog
        if (dialog != null) {
            resetWindow(dialog)
        }
        if (!isInitLoaded) {
            isInitLoaded = true
            init()
        }
    }
}
