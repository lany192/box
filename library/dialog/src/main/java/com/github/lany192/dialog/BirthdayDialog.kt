package com.github.lany192.dialog

import com.github.lany192.dialog.databinding.DialogBirthdayBinding
import com.github.lany192.utils.TimeUtils
import java.util.Calendar
import java.util.Date

/**
 * 生日选择器
 */
class BirthdayDialog(private val year: Int, private val month: Int, private val day: Int) :
    BaseDialog<DialogBirthdayBinding>() {

    private var listener: OnBirthdayListener? = null

    override fun bottomStyle(): Boolean {
        return true
    }

    override fun init() {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)
        binding.datePicker.setYearFormatter("%d年")
        binding.datePicker.setMonthFormatter("%02d月")
        binding.datePicker.setDayFormatter("%02d日")
        binding.datePicker.setOnValueChangedListener { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
            calendar.set(year, monthOfYear, dayOfMonth)
        }
        binding.datePicker.setMinDate(TimeUtils.getDate("1920-01-01"))
        binding.datePicker.setMaxDate(Date())
        binding.datePicker.updateDate(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONDAY],
            calendar[Calendar.DAY_OF_MONTH]
        )
        binding.left.setOnClickListener { cancel() }
        binding.right.setOnClickListener {
            listener?.onResult(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            cancel()
        }
    }

    fun setOnBirthdayListener(listener: OnBirthdayListener?) {
        this.listener = listener
    }

    interface OnBirthdayListener {
        fun onResult(year: Int, month: Int, day: Int)
    }
}