package com.lany192.box.sample.ui.main.menus;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.SampleRouter;
import com.github.lany192.arch.adapter.BindingAdapter;
import com.github.lany192.dialog.SimpleDialog;
import com.github.lany192.toolkit.BoxToolKit;
import com.github.lany192.utils.ChannelUtils;
import com.github.lany192.utils.ContextUtils;
import com.hjq.toast.Toaster;
import com.lany192.box.sample.databinding.ItemMenuBinding;
import com.lany192.box.sample.ui.zxing.ZxingActivity;
import com.scottyab.rootbeer.RootBeer;
import com.tencent.vasdolly.reader.ChannelReader;

import java.io.File;
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
            SampleRouter.startHtml();
        } else if (position == 5) {
            String channelApkPath = ChannelUtils.getChannelApkPath(ContextUtils.getContext(), "hello");
            Log.i("测试", "apkPath: " + channelApkPath);
            Toaster.show(channelApkPath);
        } else if (position == 6) {
            String path = ChannelUtils.getChannelApkPath(ContextUtils.getContext(), "hello_" + System.currentTimeMillis());
            File file = new File(path);
            if (!file.exists()) {
                Toaster.show("apk不存在");
                return;
            }
            String channel = ChannelReader.getChannelByV2(file);
            Log.i("测试", "v2 channel: " + channel);
            if (channel == null) {
                channel = ChannelReader.getChannelByV1(file);
            }
            Log.i("测试", "v1 channel: " + channel);
            Toaster.show("渠道信息：" + channel);
        } else if (position == 7) {
            getContext().startActivity(new Intent(getContext(), ZxingActivity.class));
        } else if (position == 8) {
            test5();
        } else if (position == 9) {
            test5();
        } else if (position == 10) {
            test5();
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
