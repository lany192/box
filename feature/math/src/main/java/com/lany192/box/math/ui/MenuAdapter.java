package com.lany192.box.math.ui;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.MathRouter;
import com.github.lany192.arch.adapter.BindingAdapter;
import com.lany192.box.math.databinding.ItemMathBinding;

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
            MathRouter.startAddition();
        } else if (position == 1) {
            MathRouter.startSubtraction();
        } else if (position == 2) {
            MathRouter.startMultiplication();
        } else if (position == 3) {
            MathRouter.startDivision();
        }
    }
}
