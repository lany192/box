package com.lany.box.adapter;

import android.support.v4.app.Fragment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TabItem {
    private String title;
    private Fragment fragment;

    public TabItem(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public TabItem(Fragment fragment) {
        this.title = fragment.getClass().getSimpleName();
        this.fragment = fragment;
    }
}
