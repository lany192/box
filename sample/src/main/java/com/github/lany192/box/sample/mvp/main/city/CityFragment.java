package com.github.lany192.box.sample.mvp.main.city;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.box.binding.BindingFragment;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.databinding.FragmentCityBinding;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CityFragment extends BindingFragment<FragmentCityBinding> implements CityContract.View {
    @Inject
    CityPresenter presenter;

    @Override
    public void onResume() {
        super.onResume();
        presenter.requestCityInfo();
    }

    @Override
    public void showItems(List<Area> items) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.collectionView.setLayoutManager(layoutManager);
        binding.collectionView.setAdapter(new BaseQuickAdapter<Area, BaseViewHolder>(R.layout.item_area, items) {

            @Override
            protected void convert(@NonNull BaseViewHolder holder, Area area) {
                holder.setText(R.id.item_area_title, area.getName());
            }
        });
    }
}