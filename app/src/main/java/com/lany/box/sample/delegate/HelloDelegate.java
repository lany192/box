package com.lany.box.sample.delegate;

import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.lany.box.delegate.ItemDelegate;
import com.lany.box.dialog.SimpleDialog;
import com.lany.box.helper.ImageLoader;
import com.lany.box.sample.R;

import butterknife.BindView;

public class HelloDelegate extends ItemDelegate<String> {
    @BindView(R.id.my_text_view)
    TextView textView;
    @BindView(R.id.my_image_view)
    ImageView imageView;

    public HelloDelegate(String data) {
        super(data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_hello;
    }

    @Override
    public void init(String data, int position) {
        textView.setText(getData());
        ImageLoader.of().showFullWidth(imageView, getData());
    }

    @Override
    public void onItemClicked(String data, int position) {
        SimpleDialog dialog = new SimpleDialog();
        dialog.setTitle("提示");
        dialog.setMessage("猜猜我是谁");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setRightBtn("确定", new SimpleDialog.OnRightListener() {
            @Override
            public void onClicked() {
                ToastUtils.show("张三");
            }
        });
        dialog.setLeftBtn("取消");
        dialog.show((FragmentActivity) getContext());
    }
}
