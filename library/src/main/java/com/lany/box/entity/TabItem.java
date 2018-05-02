package com.lany.box.entity;

import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;

public class TabItem {
    private String title;
    private Fragment fragment;
    @DrawableRes
    private int icon;
    private Class<?> clz;

    public TabItem(String title, int icon, Class<?> clz) {
        this.title = title;
        this.icon = icon;
        this.clz = clz;
    }

    public TabItem(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public TabItem(String title, Fragment fragment, @DrawableRes int icon) {
        this.title = title;
        this.fragment = fragment;
        this.icon = icon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(@DrawableRes int icon) {
        this.icon = icon;
    }
}
