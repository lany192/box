package com.github.lany192.box.sample.ui.main.index;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.github.lany192.box.sample.ui.main.city.CityFragment;

import java.util.ArrayList;
import java.util.List;

public class IndexAdapter extends FragmentStateAdapter {

    public IndexAdapter(@NonNull FragmentActivity activity) {
        super(activity);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CityFragment();
            case 1:
                return new CityFragment();
            case 2:
                return new CityFragment();
            case 3:
                return new CityFragment();
            default:
                return new CityFragment();
        }
    }

    public String[] getTitles() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            items.add("标题");
        }
        return items.toArray(new String[0]);
    }
}
