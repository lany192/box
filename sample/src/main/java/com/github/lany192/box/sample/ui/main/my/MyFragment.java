package com.github.lany192.box.sample.ui.main.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.AppRouter;
import com.github.lany192.arch.fragment.BindingFragment;
import com.github.lany192.box.sample.databinding.FragmentMyBinding;
import com.github.lany192.box.sample.viewmodel.UserViewModel;
import com.github.lany192.dialog.SimpleDialog;
import com.github.lany192.interfaces.OnSimpleListener;
import com.gyf.immersionbar.ImmersionBar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyFragment extends BindingFragment<FragmentMyBinding> {
    private MyViewModel viewModel;
    private UserViewModel userViewModel;

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)
                .navigationBarColor(android.R.color.holo_green_light)
                .init();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = getFragmentViewModel(MyViewModel.class);
        userViewModel = getAndroidViewModel(UserViewModel.class);
        userViewModel.getUserInfo().observe(this, userInfo -> binding.testView.hint(userInfo.getName()));

        binding.dialogView.setOnClickListener(v -> showDialog());
        binding.loginView.setOnClickListener(v -> AppRouter.get().login());
        binding.settingsView.setOnClickListener(v -> AppRouter.get().settings());
        binding.testView.setOnClickListener(v -> {
            userViewModel.setName("我是张三");
        });
        return root;
    }

    private void showDialog() {
        SimpleDialog dialog = new SimpleDialog();
        dialog.setTitle("提示");
        dialog.setMessage("猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁");
        dialog.setRightButton("确定", new OnSimpleListener() {
            @Override
            public void onCallback() {

            }
        });
        dialog.setLeftButton("取消", new OnSimpleListener() {
            @Override
            public void onCallback() {

            }
        });
        dialog.show(this);
    }

}
