package com.github.lany192.box.sample.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.inputmethod.EditorInfo;

import com.github.lany192.box.config.FragmentConfig;
import com.github.lany192.box.dialog.InputDialog;
import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.filter.MoneyInputFilter;
import com.hjq.toast.ToastUtils;

import butterknife.OnClick;

public class IndexFragment extends BaseFragment {

    @NonNull
    @Override
    protected FragmentConfig getConfig(FragmentConfig config) {
        return config.layoutId(R.layout.fragment_index)
                .contentColor(android.R.color.holo_green_light)
                .toolBarLayoutId(R.layout.toolbar_index);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick(R.id.custom_toolbar_edit_btn)
    void btnClicked() {
        InputDialog dialog = new InputDialog();
        dialog.setTitle("金额");
        dialog.setHint("请输入金额");
        dialog.setInputType(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
        dialog.addInputFilter(new MoneyInputFilter());
        dialog.setMaxLength(5);
        dialog.setButtonText("提交");
        dialog.setOnInputListener(new InputDialog.OnInputListener() {
            @Override
            public void onResult(CharSequence result) {
                ToastUtils.show(result);
            }
        });
        dialog.show(getActivity());
    }
}
