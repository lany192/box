package com.lany.box.sample.delegate;

import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.lany.box.delegate.ItemDelegate;
import com.lany.box.dialog.SimpleDialog;
import com.lany.box.sample.R;

import butterknife.BindView;

public class HelloDelegate extends ItemDelegate<String> {
    @BindView(R.id.my_text_view)
    TextView textView;

    public HelloDelegate(String data) {
        super(data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_hello;
    }

    @Override
    public void init() {
        textView.setText(getData());
    }

    @Override
    public void onItemClicked() {
        SimpleDialog dialog = new SimpleDialog();
        dialog.setTitle("提示");
        dialog.setMessage("猜猜我是谁");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setRightBtn("确定", new SimpleDialog.OnRightListener() {
            @Override
            public void onClicked() {

            }
        });
        dialog.setLeftBtn("取消", new SimpleDialog.OnLeftListener() {
            @Override
            public void onClicked() {

            }
        });
        dialog.show((FragmentActivity) getContext());
    }
}
