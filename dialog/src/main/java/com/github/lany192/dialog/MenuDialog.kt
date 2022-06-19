package com.github.lany192.dialog

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.lany192.dialog.databinding.DialogMenuBinding

/**
 * 通用底部菜单对话框
 */
class MenuDialog : BaseDialog<DialogMenuBinding>() {

    private var items = mutableListOf<String>()

    private var listener: OnListener? = null

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
            AdapterView.OnItemClickListener { adapterView: AdapterView<*>?, view: View?, position: Int, l: Long ->
                listener?.onItemClick(position)
                cancel()
            }
        binding.cancel.setOnClickListener { cancel() }
    }

    fun setItems(items: MutableList<String>) {
        this.items = items
    }

    fun addItem(title: String) {
        items.add(title)
    }

    fun setOnListener(listener: OnListener) {
        this.listener = listener
    }

    interface OnListener {
        suspend fun onItemClick(position: Int)
    }
}