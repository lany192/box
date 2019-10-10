package com.github.lany192.box.sample.delegate;

import android.support.v4.app.FragmentActivity;

import com.github.lany192.box.adapter.ItemViewHolder;
import com.github.lany192.box.delegate.ItemDelegate;
import com.github.lany192.box.dialog.SimpleDialog;
import com.github.lany192.box.sample.R;
import com.hjq.toast.ToastUtils;

import java.util.Locale;

public class HelloDelegate extends ItemDelegate<String> {

    public HelloDelegate(String data) {
        super(data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_hello;
    }

    @Override
    public void init(ItemViewHolder holder, String pic, int position) {
        holder.setText(R.id.my_text_view, String.format(Locale.getDefault(), "图片%d", position + 1));
        holder.setImageFullWidth(R.id.my_image_view, pic);
    }

    @Override
    public void onItemClicked(String pic, int position) {
        SimpleDialog dialog = new SimpleDialog();
        dialog.setTitle("提示");
        dialog.setMessage("猜猜我是谁");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setRightBtn("确定", () -> ToastUtils.show(pic));
        dialog.setLeftBtn("取消");
        dialog.show((FragmentActivity) getContext());
    }
}
