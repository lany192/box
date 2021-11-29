package com.github.lany192.box.sample.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.github.lany192.box.sample.ui.main.discover.DiscoverFragment;
import com.github.lany192.box.sample.ui.main.index.IndexFragment;
import com.github.lany192.box.sample.ui.main.message.MessageFragment;
import com.github.lany192.box.sample.ui.main.my.MyFragment;

public class MainAdapter extends FragmentStateAdapter {

    public MainAdapter(@NonNull FragmentActivity activity) {
        super(activity);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new IndexFragment();
            case 1:
                return new DiscoverFragment();
            case 2:
                return new MessageFragment();
            case 3:
                return new MyFragment();
            default:
                return new IndexFragment();
        }
    }
}
