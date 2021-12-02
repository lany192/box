package com.github.lany192.box.sample.ui.main.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.box.binding.BindingFragment;
import com.github.lany192.box.sample.databinding.FragmentMessageBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MessageFragment extends BindingFragment<FragmentMessageBinding> {
    private MessageViewModel viewModel;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = getFragmentViewModel(MessageViewModel.class);
        return root;
    }
}
