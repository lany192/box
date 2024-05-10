package com.lany192.box.math.ui.addition;

import androidx.annotation.NonNull;

import com.github.lany192.arch.adapter.BindingAdapter;
import com.lany192.box.math.databinding.ItemAdditionBinding;
import com.lany192.box.math.repository.MathItem;

import java.util.List;

public class AdditionAdapter extends BindingAdapter<MathItem, ItemAdditionBinding> {

    public AdditionAdapter(@NonNull List<MathItem> items) {
        super(items);
    }

    @Override
    protected void convert(@NonNull ItemAdditionBinding binding, @NonNull MathItem item, int position) {
        binding.left.setText(String.valueOf(item.getLeft()));
        binding.right.setText(String.valueOf(item.getRight()));
    }

    @Override
    public void onItemClicked(@NonNull ItemAdditionBinding binding, @NonNull MathItem item, int position) {

    }
}