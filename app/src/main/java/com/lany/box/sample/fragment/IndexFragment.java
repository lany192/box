package com.lany.box.sample.fragment;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

import com.hjq.toast.ToastUtils;
import com.lany.box.dialog.InputDialog;
import com.lany.box.fragment.BaseFragment;
import com.lany.box.sample.R;
import com.lany.box.sample.filter.MoneyInputFilter;

import butterknife.OnClick;

public class IndexFragment extends BaseFragment {
    @Override
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_index;
    }

    @Override
    protected boolean hasToolbar() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
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
