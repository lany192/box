package com.github.lany192.box.sample.ui.main.index.article;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.lany192.box.fragment.BindingFragment;
import com.github.lany192.box.sample.databinding.FragmentArticleBinding;
import com.github.lany192.multitype.adapter.MultiAdapter;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ArticleFragment extends BindingFragment<FragmentArticleBinding> {

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        ArticleViewModel viewModel = getFragmentViewModel(ArticleViewModel.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        MultiAdapter adapter = new MultiAdapter(new ArrayList<>());
        binding.recyclerView.setAdapter(adapter);
        viewModel.getItems().observe(this, adapter::setNewInstance);
        viewModel.getLoading().observe(this, loading -> {
            if (loading) {
                showLoading();
            } else {
                showContent();
            }
        });
        return root;
    }
}