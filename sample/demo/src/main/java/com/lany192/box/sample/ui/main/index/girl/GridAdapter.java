package com.lany192.box.sample.ui.main.index.girl;

import androidx.annotation.NonNull;

import com.github.lany192.arch.adapter.BindingAdapter;
import com.lany192.box.demo.databinding.ItemGirlBinding;

import java.util.List;

public class GridAdapter extends BindingAdapter<String, ItemGirlBinding> {

    public GridAdapter(@NonNull List<String> data) {
        super(data);
    }

    @Override
    protected void convert(@NonNull ItemGirlBinding binding, String item, int position) {
    }
}
