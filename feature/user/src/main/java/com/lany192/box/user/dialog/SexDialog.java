package com.lany192.box.user.dialog;


import android.graphics.Color;

import com.github.lany192.dialog.BaseDialog;
import com.github.lany192.utils.PhoneUtils;
import com.hjq.toast.Toaster;
import com.lany192.box.user.R;
import com.lany192.box.user.databinding.DialogSexBinding;

/**
 * 性别选择对话框
 */
public class SexDialog extends BaseDialog<DialogSexBinding> {
    private OnSexListener mListener;

    private boolean isMan;

    public SexDialog(boolean isMan) {
        this.isMan = isMan;
    }

    @Override
    protected boolean bottomStyle() {
        return true;
    }

    @Override
    protected void init() {
        if (isMan) {
            manClicked();
        } else {
            womanClicked();
        }
        binding.cancel.setOnClickListener(v -> cancel());
        binding.confirm.setOnClickListener(v -> {
            cancel();
            if (mListener != null) {
                mListener.onResult(isMan);
            }
        });
        binding.boy.setOnClickListener(v -> manClicked());
        binding.girl.setOnClickListener(v -> womanClicked());
        Toaster.show("是否有导航栏：" + PhoneUtils.hasNavigationBar());
    }

    void manClicked() {
        isMan = true;
        binding.boy.setIcon(R.mipmap.sex_man_selected);
        binding.boy.setTextColor(Color.parseColor("#4a97ff"));
        binding.girl.setIcon(R.mipmap.sex_woman_normal);
        binding.girl.setTextColor(Color.parseColor("#babcc8"));
    }

    void womanClicked() {
        isMan = false;
        binding.boy.setIcon(R.mipmap.sex_man_normal);
        binding.boy.setTextColor(Color.parseColor("#babcc8"));
        binding.girl.setIcon(R.mipmap.sex_woman_selected);
        binding.girl.setTextColor(Color.parseColor("#ff889b"));
    }

    public void setOnSexListener(OnSexListener listener) {
        this.mListener = listener;
    }

    public interface OnSexListener {
        void onResult(boolean isMan);
    }
}