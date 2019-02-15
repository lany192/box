package com.lany.box.sample.delegate;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lany.box.delegate.ItemDelegate;
import com.lany.box.dialog.SimpleDialog;
import com.lany.box.helper.ImageHelper;
import com.lany.box.interfaces.OnImageLoadListener;
import com.lany.box.sample.R;
import com.lany.box.utils.PhoneUtils;

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
    public void init() {
        textView.setText(getData());
        ImageHelper.of().show(imageView, "http://d.hiphotos.baidu.com/image/pic/item/4b90f603738da977625f2cf7bd51f8198718e3fe.jpg", new OnImageLoadListener() {
            @Override
            public void onFinish(View view, int width, int height) {
                int maxPicWidth = PhoneUtils.getDeviceWidth();
                if (width < maxPicWidth) {
                    maxPicWidth = width;
                }
                view.setLayoutParams(new LinearLayout.LayoutParams(maxPicWidth, maxPicWidth * height / width));
                view.setVisibility(View.VISIBLE);
            }
        });
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
