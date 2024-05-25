package com.github.lany192.dialog

import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.lany192.dialog.databinding.DialogMenuBinding
import com.github.lany192.extensions.setGone

/**
 * 通用底部菜单对话框
 */
class MenuDialog : BaseDialog<DialogMenuBinding>() {

    private var items = mutableListOf<String>()

    private var listener: OnItemListener? = null

    private var title: String? = null;

    override fun bottomStyle(): Boolean {
        return true
    }

    override fun init() {
        binding.listView.adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_dialog_menu,
            R.id.title, items
        )
        binding.listView.onItemClickListener =
            AdapterView.OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
                listener?.onItemClick(position)
                cancel()
            }
        binding.cancel.setOnClickListener { cancel() }
        binding.title.setGone(TextUtils.isEmpty(title))
        binding.title.text = title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setItems(items: MutableList<String>) {
        this.items = items
    }

    fun addItem(title: String) {
        items.add(title)
    }

    fun setOnItemListener(listener: OnItemListener) {
        this.listener = listener
    }

    interface OnItemListener {
        fun onItemClick(position: Int)
    }
}