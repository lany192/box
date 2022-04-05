package com.github.lany192.dialog;

import android.widget.ArrayAdapter;

import com.github.lany192.dialog.databinding.DialogMenuBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用底部菜单对话框
 */
public class MenuDialog extends BottomDialog<DialogMenuBinding> {
    private List<String> items = new ArrayList<>();
    private OnListener listener;

    @Override
    protected void init() {
        binding.listView.setAdapter(new ArrayAdapter<>(getContext(),
                R.layout.item_dialog_menu,
                R.id.title,
                items));
        binding.listView.setOnItemClickListener((adapterView, view, position, l) -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
            cancel();
        });
        binding.cancel.setOnClickListener(v -> cancel());
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void addItem(String title) {
        this.items.add(title);
    }

    public void setOnListener(OnListener listener) {
        this.listener = listener;
    }

    public interface OnListener {

        void onItemClick(int position);
    }
}