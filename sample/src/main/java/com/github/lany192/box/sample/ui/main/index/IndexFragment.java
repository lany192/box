package com.github.lany192.box.sample.ui.main.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.github.lany192.box.binding.BindingFragment;
import com.github.lany192.box.dialog.LoadingDialog;
import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.fragment.FragmentConfig;
import com.github.lany192.box.fragment.TabPager;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.databinding.FragmentIndexBinding;
import com.github.lany192.box.sample.fragment.DemoFragment;
import com.github.lany192.box.sample.fragment.GirlFragment;
import com.github.lany192.box.sample.fragment.SubTabFragment;
import com.github.lany192.box.sample.ui.main.city.CityAdapter;
import com.github.lany192.box.sample.ui.main.city.CityFragment;
import com.github.lany192.box.sample.ui.main.city.CityViewModel;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IndexFragment extends BindingFragment<FragmentIndexBinding> {
    private IndexViewModel viewModel;
    private CityAdapter adapter;
    private LoadingDialog loadingDialog;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(IndexViewModel.class);
        getLifecycle().addObserver(viewModel);

        binding.toolbar.setTitle("首页");
        ImmersionBar.with(this).titleBar(binding.toolbar).statusBarDarkFont(false).init();
        adapter = new CityAdapter(new ArrayList<>());
        binding.recyclerView.setAdapter(adapter);
        viewModel.getItems().observe(this, areas -> adapter.setNewInstance(areas));
        viewModel.getLoading().observe(this, loading -> {
            if (loading) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog();
                }
                if (!loadingDialog.isAdded()) {
                    loadingDialog.show(getParentFragmentManager(), "TAG" + System.currentTimeMillis());
                }
            } else {
                if (loadingDialog != null && loadingDialog.isAdded()) {
                    loadingDialog.cancel();
                    loadingDialog = null;
                }
            }
        });
        return root;
    }
}
