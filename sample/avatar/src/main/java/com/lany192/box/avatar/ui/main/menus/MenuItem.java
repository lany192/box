package com.lany192.box.avatar.ui.main.menus;

public class MenuItem {
    private final String name;
    private final int resId;

    public MenuItem(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public int getResId() {
        return resId;
    }
}
