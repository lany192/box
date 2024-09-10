package com.lany192.box.math.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ViewModelActivity;
import com.github.lany192.arch.utils.BarUtils;
import com.github.lany192.decoration.LinearDecoration;
import com.lany192.box.math.databinding.ActivityMathBinding;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/math/main")
public class MathActivity extends ViewModelActivity<MathViewModel, ActivityMathBinding> {
//    @Override
//    public void initImmersionBar() {
//        BarUtils.init(this).keyboardEnable(true).titleBar(binding.toolbar).init();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> items = new ArrayList<>();
        items.add("加法");
        items.add("减法");
        items.add("乘法");
        items.add("除法");
        binding.recyclerView.addItemDecoration(new LinearDecoration(1, Color.BLACK, RecyclerView.VERTICAL));
        binding.recyclerView.setAdapter(new MenuAdapter(items));
    }
}