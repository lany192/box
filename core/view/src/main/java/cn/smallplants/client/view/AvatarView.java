package cn.smallplants.client.view;

import static android.widget.RelativeLayout.ALIGN_PARENT_LEFT;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import com.github.lany192.utils.ImageUtils;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import cn.smallplants.client.model.response.UserItem;


/**
 * 喜欢头像
 */
public class AvatarView extends HorizontalScrollView {
    //默认图片大小
    private static final int DEFAULT_PIC_SIZE = 33;
    //默认图片数量
    private static final int DEFAULT_PIC_COUNT = 6;
    //默认图片偏移百分比 0～1
    private static final float DEFAULT_PIC_OFFSET = 0.5f;
    private List<ShapeableImageView> headList;
    private int picSize = dp2Px(DEFAULT_PIC_SIZE);
    private int picCount = DEFAULT_PIC_COUNT;
    private float picOffset = DEFAULT_PIC_OFFSET;

    public AvatarView(Context context) {
        super(context);
        init(null);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            //初始化自定义属性
            TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.ApproveListLayout);
            picCount = ta.getInt(R.styleable.ApproveListLayout_pic_count, DEFAULT_PIC_COUNT);
            picSize = (int) ta.getDimension(R.styleable.ApproveListLayout_pic_size, picSize);
            picOffset = ta.getFloat(R.styleable.ApproveListLayout_pic_offset, DEFAULT_PIC_OFFSET);
            picOffset = picOffset > 1 ? 1 : picOffset;
            ta.recycle();
        }
        setHorizontalScrollBarEnabled(false);
        //定义一个RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        int offset = picSize - (int) (picSize * picOffset);
        headList = new ArrayList<>(picCount);
        //循环把CircleImageView塞到RelativiLayout中，根据偏移量来摆放位置
        for (int i = 0; i < picCount; i++) {
            ShapeableImageView head = new ShapeableImageView(getContext());
            head.setId(head.hashCode() + i);
//            head.setBorderColor(Color.WHITE);
//            head.setBorderWidth(dp2Px(1));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(picSize, picSize);
            params.addRule(ALIGN_PARENT_LEFT);
            params.setMargins((picCount - i - 1) * offset, 0, 0, 0);
            relativeLayout.addView(head, params);
            headList.add(head);
        }
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //这里是关键：把定义的RelativeLayout放到布局中，这样才能显示出来
        this.addView(relativeLayout);
    }

    int dp2Px(float dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void setPicSize(int picSize) {
        this.picSize = picSize;
    }

    public void setPicCount(int picCount) {
        this.picCount = picCount;
    }

    public void setPicOffset(int offset) {
        picOffset = dp2Px(offset);
    }

    public void setData(List<UserItem> items) {
        if (items == null) {
            return;
        }
        hideAllHeads();
        int i = picCount - 1;
        for (UserItem user : items) {
            ImageUtils.show(headList.get(i), user.getAvatar());
            headList.get(i).setVisibility(View.VISIBLE);
            if (i == 0) {
                break;
            }
            --i;
        }
    }

    private void hideAllHeads() {
        for (ShapeableImageView head : headList) {
            head.setVisibility(View.GONE);
        }
    }
}