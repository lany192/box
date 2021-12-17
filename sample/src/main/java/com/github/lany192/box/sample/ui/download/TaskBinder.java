package com.github.lany192.box.sample.ui.download;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.box.sample.databinding.ItemTaskBinding;

public class TaskBinder extends ItemBinder<Task, ItemTaskBinding> {

    @Override
    public void bind(ItemTaskBinding binding, Task task, int position) {
//        binding.title.setText(task.getName());


    }

    @NonNull
    @Override
    public ItemTaskBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, int i) {
        return ItemTaskBinding.inflate(layoutInflater, viewGroup, false);
    }
}
