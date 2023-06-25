package cn.smallplants.client.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.view.BindingView;

import cn.smallplants.client.view.databinding.ViewCountTextBinding;

public class CountTextView extends BindingView<ViewCountTextBinding> {

    public CountTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountTextView(@NonNull Context context) {
        super(context);
    }

    public CountTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(@Nullable AttributeSet attributeSet) {

    }

    public void setText(String count, String title) {
        binding.count.setText(count);
        binding.title.setText(title);
    }
}
