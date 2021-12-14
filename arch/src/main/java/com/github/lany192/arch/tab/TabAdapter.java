package com.github.lany192.arch.tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentStateAdapter {
    private final List<TabItem> items;

    public TabAdapter(@NonNull FragmentActivity activity, @NonNull List<TabItem> items) {
        super(activity);
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return items.get(position).getFragment();
    }

    public List<CharSequence> getTitles() {
        List<CharSequence> titles = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            titles.add(items.get(i).getTitle().toString());
        }
        return titles;
    }
}
