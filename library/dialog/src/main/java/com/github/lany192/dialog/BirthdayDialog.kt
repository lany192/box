package com.github.lany192.dialog

import com.github.lany192.dialog.databinding.DialogBirthdayBinding
import com.github.lany192.utils.TimeUtils
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

/**
 * 生日选择器
 */
class BirthdayDialog(private val localDate: LocalDate) : BaseDialog<DialogBirthdayBinding>() {

    private var listener: OnBirthdayListener? = null

    override fun bottomStyle(): Boolean {
        return true
    }

    override fun init() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = TimeUtils.localDate2Timestamp(localDate)
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
        binding.close.setOnClickListener { cancel() }
        binding.confirm.setOnClickListener {
            listener?.onResult(TimeUtils.date2LocalDate(calendar.time))
            cancel()
        }
    }

    fun setOnBirthdayListener(listener: OnBirthdayListener?) {
        this.listener = listener
    }

    interface OnBirthdayListener {
        fun onResult(birthday: LocalDate)
    }
}