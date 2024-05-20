package com.lany192.box.math.ui;

import androidx.annotation.NonNull;

import com.github.lany192.arch.adapter.BindingAdapter;
import com.lany192.box.math.databinding.ItemMathBinding;
import com.lany192.box.math.ui.addition.AdditionRouter;
import com.lany192.box.math.ui.division.DivisionRouter;
import com.lany192.box.math.ui.multiplication.MultiplicationRouter;
import com.lany192.box.math.ui.subtraction.SubtractionRouter;

import java.util.List;

public class MenuAdapter extends BindingAdapter<String, ItemMathBinding> {

    public MenuAdapter(@NonNull List<String> items) {
        super(items);
    }

    @Override
    protected void convert(@NonNull ItemMathBinding binding, @NonNull String item, int position) {
        binding.title.setText(item);
    }

    @Override
    public void onItemClicked(@NonNull ItemMathBinding binding, @NonNull String item, int position) {
        if (position == 0) {
            AdditionRouter.start();
        } else if (position == 1) {
            SubtractionRouter.start();
        } else if (position == 2) {
            MultiplicationRouter.start();
        } else if (position == 3) {
            DivisionRouter.start();
        }
    }
}
