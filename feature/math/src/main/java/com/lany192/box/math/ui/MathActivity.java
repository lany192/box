package com.lany192.box.math.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.BoxActivity;
import com.lany192.box.math.databinding.ActivityMathBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/math/main")
public class MathActivity extends BoxActivity<MathViewModel, ActivityMathBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}