package com.github.lany192.box.adapter

import androidx.fragment.app.Fragment

class TabItem {
    var title: String
    var fragment: Fragment

    constructor(title: String, fragment: Fragment) {
        this.title = title
        this.fragment = fragment
    }

    constructor(fragment: Fragment) {
        title = fragment.javaClass.simpleName
        this.fragment = fragment
    }
}