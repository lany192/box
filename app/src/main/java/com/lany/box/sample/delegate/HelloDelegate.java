package com.lany.box.sample.delegate;

import android.widget.TextView;

import com.lany.box.delegate.ItemDelegate;
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
}
