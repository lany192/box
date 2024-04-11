package com.lany192.box.sample.ui.main.menus;

import androidx.annotation.NonNull;

import com.github.lany192.arch.adapter.BindingAdapter;
import com.hjq.toast.Toaster;
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
            test1();
        } else if (position == 1) {
            test2();
        } else if (position == 2) {
            test3();
        } else if (position == 3) {
            test4();
        } else if (position == 4) {
            test5();
        }
    }

    private void test1() {
        Toaster.show("点击了1");
    }

    private void test2() {
        Toaster.show("点击了1");
    }

    private void test3() {
        Toaster.show("点击了1");
    }

    private void test4() {
        Toaster.show("点击了1");
    }

    private void test5() {
        Toaster.show("点击了1");
    }
}
