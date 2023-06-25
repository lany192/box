package cn.smallplants.client.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.interfaces.OnSimpleListener;
import com.github.lany192.text.IconTextView;
import com.github.lany192.utils.DensityUtils;
import com.noober.background.drawable.DrawableCreator;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AttentionView extends IconTextView {
    private OnSimpleListener listener;
    private int status;

    public AttentionView(@NonNull Context context) {
        this(context, null);
    }

    public AttentionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AttentionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(v -> {
            if (status == 0) {
                status = 1;
            } else if (status == 1) {
                status = 0;
            } else if (status == 2) {
                status = 3;
            } else if (status == 3) {
                status = 2;
            }
            setStatus(status);
            if (listener != null) {
                listener.onCallback();
            }
        });
    }

    /**
     * 关注状态.0表示未关注；1表示已关注对方，对方没有关注自己；2表示对方关注了自己，自己没有关注对方；3表示相互关注")
     */
    public void setStatus(int status) {
        this.status = status;
        float radius = DensityUtils.dp2px(4);
        setBackgroundDrawable(new DrawableCreator.Builder()
                .setCornersRadius(radius, radius, radius, radius)
                .setSolidColor(getResources().getColor(R.color.black))
                .build());
        switch (status) {
            case 0:
            case 2:
                setText("关注");
                setTextColor(Color.WHITE);
                setIcon(R.drawable.vector_attention_add);
                break;
            case 1:
                setText("已关注");
                setTextColor(Color.WHITE);
                setIcon(0);
                break;
            case 3:
                setText("互关");
                setTextColor(Color.WHITE);
                setIcon(R.drawable.vector_attention_together);
                break;
        }
    }

    public void setOnSimpleListener(OnSimpleListener listener) {
        this.listener = listener;
    }
}
