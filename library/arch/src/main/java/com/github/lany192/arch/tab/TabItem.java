package com.github.lany192.arch.tab;

import androidx.fragment.app.Fragment;

public class TabItem {
    private final CharSequence title;
    private final Fragment fragment;

    public TabItem(CharSequence title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public CharSequence getTitle() {
        return title;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
