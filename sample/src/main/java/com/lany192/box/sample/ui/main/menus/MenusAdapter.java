package com.lany192.box.sample.ui.main.menus;

import androidx.annotation.NonNull;

import com.github.lany192.arch.adapter.BindingAdapter;
import com.lany192.box.sample.databinding.ItemMenuBinding;

import java.util.List;

public class MenusAdapter extends BindingAdapter<MenuItem, ItemMenuBinding> {

    public MenusAdapter(@NonNull List<MenuItem> data) {
        super(data);
    }

    @Override
    protected void convert(@NonNull ItemMenuBinding binding, MenuItem item, int position) {
        binding.name.setText(item.getName());
    }

    @Override
    public void onItemClicked(@NonNull ItemMenuBinding binding, MenuItem item, int position) {
        if (position == 0) {

        } else if (position == 1) {

        } else if (position == 2) {

        } else if (position == 3) {

        } else if (position == 4) {

        }
    }
}
