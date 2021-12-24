package com.github.lany192.box.sample.ui.main.my;

import com.alibaba.android.arouter.AppRouter;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.fragment.BindingFragment;
import com.github.lany192.box.sample.databinding.FragmentMyBinding;
import com.github.lany192.box.sample.viewmodel.UserViewModel;
import com.github.lany192.dialog.SimpleDialog;
import com.github.lany192.interfaces.OnSimpleListener;
import com.gyf.immersionbar.ImmersionBar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/fragment/my")
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

    @Override
    public void initView() {
        viewModel = getFragmentViewModel(MyViewModel.class);
        userViewModel = getAndroidViewModel(UserViewModel.class);
        userViewModel.getUserInfo().observe(this, userInfo -> binding.testView.hint(userInfo.getName()));
        binding.downloadView.setOnClickListener(v -> AppRouter.get().download());
        binding.dialogView.setOnClickListener(v -> showDialog());
        binding.loginView.setOnClickListener(v -> AppRouter.get().login());
        binding.settingsView.setOnClickListener(v -> AppRouter.get().settings());
        binding.testView.setOnClickListener(v -> {
            userViewModel.setName("我是张三");
        });
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
