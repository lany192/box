package cn.smallplants.client.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.text.IconTextView;

public class LikeView extends IconTextView {
    private boolean like;
    private long count;

    public LikeView(@NonNull Context context) {
        this(context, null);
    }

    public LikeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(v -> {
            if (like) {
                this.count = count - 1;
            } else {
                this.count = count + 1;
            }
            this.like = !like;
            update();
        });
    }

    public void setLike(boolean like, long count) {
        this.like = like;
        this.count = count;
        update();
    }

    private void update() {
        if (like) {
            setTextColorId(R.color.red);
            setIcon(R.drawable.ico_attentioned);
            setIconTintResource(R.color.red);
        } else {
            setTextColorId(R.color.text_3level);
            setIcon(R.drawable.ico_attention);
            setIconTintResource(R.color.text_3level);
        }
        if (count > 0) {
            setText(String.valueOf(count));
        } else {
            setText("0");
        }
    }
}
