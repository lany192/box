package com.github.lany192.box.sample.ui.main.index;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.github.lany192.box.sample.ui.main.discover.DiscoverFragment;
import com.github.lany192.box.sample.ui.main.index.girl.GirlFragment;

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
                return new DiscoverFragment();
            case 1:
                return new DiscoverFragment();
            default:
                return new GirlFragment();
        }
    }

    public String[] getTitles() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            items.add("标题" + (i + 1));
        }
        return items.toArray(new String[0]);
    }
}
