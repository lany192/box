package com.github.lany192.box.sample.ui.main.city;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.github.lany192.box.adapter.ViewBindingAdapter;

import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.databinding.ItemAreaBinding;
import com.github.lany192.utils.ImageUtils;

import java.util.List;

public class CityAdapter extends ViewBindingAdapter<Area, ItemAreaBinding> {

    public CityAdapter(List<Area> items) {
        super(items);
    }

    @Override
    protected ItemAreaBinding getBinding(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return ItemAreaBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bind(ItemAreaBinding binding, Area area, int position) {
        binding.title.setText(area.getName());
        int count = area.getSubarea() != null ? area.getSubarea().size() : 0;
        binding.desc.setText("下辖" + count + "个区/市");
        ImageUtils.show(binding.image, "https://drimg02.scbao.com/190602/330471-1Z60214395123.jpg");
    }
}
