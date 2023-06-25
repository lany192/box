package cn.smallplants.client.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.view.BindingView;

import cn.smallplants.client.App;
import cn.smallplants.client.view.databinding.ViewBottomBinding;

public class BottomView extends BindingView<ViewBottomBinding> {
    private OnTabListener listener;

    public BottomView(@NonNull Context context) {
        super(context);
    }

    public BottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(@Nullable AttributeSet attrs) {
        binding.index.setOnClickListener(v -> {
            setCurrentItem(0);
            if (listener != null) {
                listener.onSelected(0);
            }
        });
        binding.location.setOnClickListener(v -> {
            setCurrentItem(1);
            if (listener != null) {
                listener.onSelected(1);
            }
        });
        binding.message.setOnClickListener(v -> {
            if (!App.getUser().isLogin()) {
                App.getLogin().startLogin();
                return;
            }
            setCurrentItem(2);
            if (listener != null) {
                listener.onSelected(2);
            }
        });
        binding.my.setOnClickListener(v -> {
            if (!App.getUser().isLogin()) {
                App.getLogin().startLogin();
                return;
            }
            setCurrentItem(3);
            if (listener != null) {
                listener.onSelected(3);
            }
        });
        binding.publish.setOnClickListener(v -> {
            if (!App.getUser().isLogin()) {
                App.getLogin().startLogin();
                return;
            }
            if (listener != null) {
                listener.onPublish();
            }
        });
    }

    public void setOnTabListener(OnTabListener listener) {
        this.listener = listener;
    }

    public void setCurrentItem(int position) {
        binding.index.setSelected(false);
        binding.location.setSelected(false);
        binding.message.setSelected(false);
        binding.my.setSelected(false);

        switch (position) {
            case 0:
                binding.index.setSelected(true);
                break;
            case 1:
                binding.location.setSelected(true);
                break;
            case 2:
                binding.message.setSelected(true);
                break;
            case 3:
                binding.my.setSelected(true);
                break;
        }
    }

    /**
     * @author lyg
     */
    public interface OnTabListener {
        void onSelected(int position);

        void onPublish();
    }
}
