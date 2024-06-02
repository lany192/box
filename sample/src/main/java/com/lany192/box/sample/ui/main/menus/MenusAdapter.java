package com.lany192.box.sample.ui.main.menus;

import android.util.Log;

import androidx.annotation.NonNull;

import com.github.lany192.arch.adapter.BindingAdapter;
import com.github.lany192.arch.utils.DeviceId;
import com.github.lany192.dialog.SimpleDialog;
import com.github.lany192.toolkit.BoxToolKit;
import com.github.lany192.utils.ChannelUtils;
import com.hjq.toast.Toaster;
import com.lany192.box.browser.ui.BrowserRouter;
import com.lany192.box.hello.ui.HelloRouter;
import com.lany192.box.login.ui.LoginRouter;
import com.lany192.box.math.ui.MathRouter;
import com.lany192.box.sample.databinding.ItemMenuBinding;
import com.lany192.box.sample.ui.blur.BlurRouter;
import com.lany192.box.sample.ui.database.DatabaseRouter;
import com.lany192.box.sample.ui.encrypt.EncryptRouter;
import com.lany192.box.sample.ui.html.HtmlRouter;
import com.lany192.box.sample.ui.image.ImageRouter;
import com.lany192.box.sample.ui.settings.SettingsRouter;
import com.lany192.box.sample.ui.transformation.TransformationRouter;
import com.lany192.box.sample.ui.zxing.ZxingRouter;
import com.lany192.box.user.ui.UserInfoRouter;
import com.scottyab.rootbeer.RootBeer;

import java.util.List;

public class MenusAdapter extends BindingAdapter<MenuItem, ItemMenuBinding> {

    public MenusAdapter(@NonNull List<MenuItem> data) {
        super(data);
    }

    @Override
    protected void convert(@NonNull ItemMenuBinding binding, MenuItem item, int position) {
        binding.name.setText(item.getName());
        binding.name.setIcon(item.getResId());
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
            Toaster.show("是否是模拟器：" + BoxToolKit.isEmulator());
        } else if (position == 4) {
            HtmlRouter.start();
        } else if (position == 5) {
            String channelApkPath = ChannelUtils.getChannelApkPath(getContext(), "hello");
            Log.i("测试", "apkPath: " + channelApkPath);
            Toaster.show(channelApkPath);
        } else if (position == 6) {
            String path = ChannelUtils.getChannelApkPath(getContext(), "hello_" + System.currentTimeMillis());
            String channel = ChannelUtils.getChannelByPath(path);
            Toaster.show("渠道信息：" + channel);
        } else if (position == 7) {
            ZxingRouter.start();
        } else if (position == 8) {
            TransformationRouter.start();
        } else if (position == 9) {
            DatabaseRouter.start();
        } else if (position == 10) {
            Toaster.show(DeviceId.get().getDeviceId());
        } else if (position == 11) {
            EncryptRouter.start();
        } else if (position == 12) {
            HelloRouter.start();
        } else if (position == 13) {
            BrowserRouter.start("测试", "https//www.baidu.com");
        } else if (position == 14) {
            LoginRouter.start();
        } else if (position == 15) {
            MathRouter.start();
        } else if (position == 16) {
            UserInfoRouter.start();
        } else if (position == 17) {
            ImageRouter.start();
        } else if (position == 18) {
            SettingsRouter.start();
        } else if (position == 19) {
            BlurRouter.start();
        }
    }

    private void test1() {
        if (new RootBeer(getContext()).isRooted()) {
            SimpleDialog dialog = new SimpleDialog();
            dialog.setMessage("检测到当前手机已经被root，存在数据不安全情况。为保证良好的用户体验，请选择在非root手机上使用本软件。");
            dialog.setRightButton("取消");
            dialog.show();
        } else {
            Toaster.show("未发现root");
        }
    }

    private void test2() {
        Toaster.show(BoxToolKit.getCurrentProcess());
    }

    private void test3() {
        Toaster.show("计算结果：" + hello(1, 2));
    }

    private void test4() {
        Toaster.show("点击了1");
    }

    private void test5() {
        Toaster.show("点击了");
    }

    private int hello(int x, int y) {
        Log.d("测试", x + "+" + y + "==" + (x + y));
        return x + y;
    }
}
