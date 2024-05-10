package com.lany192.box.math.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.BoxActivity;
import com.github.lany192.arch.adapter.BindingAdapter;
import com.lany192.box.math.databinding.ActivityMathBinding;
import com.lany192.box.math.databinding.ItemMathBinding;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/math/main")
public class MathActivity extends BoxActivity<MathViewModel, ActivityMathBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> items = new ArrayList<>();
        items.add("加法");
        items.add("加法");
        items.add("加法");
        items.add("加法");
        binding.recyclerView.setAdapter(new BindingAdapter<String, ItemMathBinding>(items) {

            @Override
            protected void convert(@NonNull ItemMathBinding binding, @NonNull String item, int position) {
                binding.title.setText(item);
            }
        });
    }
}