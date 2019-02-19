package com.lany.box.sample.delegate;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lany.box.delegate.ItemDelegate;
import com.lany.box.dialog.SimpleDialog;
import com.lany.box.helper.ImageHelper;
import com.lany.box.sample.R;
import com.lany.box.utils.PhoneUtils;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

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
        String picUrl = "http://d.hiphotos.baidu.com/image/pic/item/4b90f603738da977625f2cf7bd51f8198718e3fe.jpg";
        if (getHolder().getAdapterPosition() % 2 == 0) {
            picUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550479961661&di=58f0ec0ba23d0bf8cff25499d8a93623&imgtype=0&src=http%3A%2F%2Fbannerdesign.cn%2Fwp-content%2Fuploads%2F2015%2F02%2F20150204014336322.jpg";
        } else if (getHolder().getAdapterPosition() % 3 == 0) {
            picUrl = "http://e.hiphotos.baidu.com/image/pic/item/eac4b74543a982265bd540e38782b9014b90ebda.jpg";
        }
        ImageHelper.of().show(imageView, picUrl, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
                int screenWidth = PhoneUtils.getDeviceWidth();
                view.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, screenWidth * bitmap.getHeight() / bitmap.getWidth()));
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
        dialog.setRightBtn("确定");
        dialog.setLeftBtn("取消");
        dialog.show((FragmentActivity) getContext());
    }
}
