package com.github.lany192.box.sample.ui.main.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.box.fragment.BindingFragment;
import com.github.lany192.box.sample.databinding.FragmentMyBinding;
import com.github.lany192.box.sample.ui.about.AboutActivity;
import com.github.lany192.box.sample.ui.login.LoginActivity;
import com.github.lany192.box.sample.ui.settings.SettingsActivity;
import com.github.lany192.dialog.SimpleDialog;
import com.github.lany192.interfaces.OnSimpleListener;
import com.gyf.immersionbar.ImmersionBar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyFragment extends BindingFragment<FragmentMyBinding> {
    private MyViewModel viewModel;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = getFragmentViewModel(MyViewModel.class);
//        binding.myOrderView.setOnClickListener(v -> showDialog());
//        binding.myWalletView.setOnClickListener(v -> startActivity(new Intent(getActivity(), AboutActivity.class)));
//        binding.myReleaseView.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoginActivity.class)));
        binding.settingsView.setOnClickListener(v -> startActivity(new Intent(getActivity(), SettingsActivity.class)));
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

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)
                .navigationBarColor(android.R.color.holo_green_light)
                .init();
    }
}
