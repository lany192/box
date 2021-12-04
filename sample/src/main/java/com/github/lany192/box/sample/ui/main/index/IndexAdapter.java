package com.github.lany192.box.sample.ui.main.index;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.github.lany192.box.sample.ui.main.discover.DiscoverFragment;
import com.github.lany192.box.sample.ui.main.index.article.ArticleFragment;

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
        if (position == 0) {
            return new DiscoverFragment();
        }
        if (position % 2 == 1) {
            return new DiscoverFragment();
        } else {
            return new ArticleFragment();
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
