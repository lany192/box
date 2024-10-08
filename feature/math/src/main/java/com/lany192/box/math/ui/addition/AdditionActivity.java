package com.lany192.box.math.ui.addition;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ViewModelActivity;
import com.github.lany192.arch.utils.BarUtils;
import com.lany192.box.math.databinding.ActivityAdditionBinding;
import com.lany192.box.math.repository.MathItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/math/addition")
public class AdditionActivity extends ViewModelActivity<AdditionViewModel, ActivityAdditionBinding> {
//    @Override
//    public void initImmersionBar() {
//        BarUtils.init(this).keyboardEnable(true).titleBar(binding.toolbar).init();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Random random = new Random();
        List<MathItem> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            items.add(new MathItem(0, random.nextInt(), random.nextInt()));
        }
        binding.viewPager.setAdapter(new AdditionAdapter(items));
    }
}