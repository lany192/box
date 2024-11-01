package com.github.lany192.link

import android.content.Context
import android.widget.TextView

fun TextView.applyLinks(vararg links: Link) {
    val builder = LinkBuilder.on(this)
    links.forEach { builder.addLink(it) }

    builder.build()
}


fun TextView.applyLinks(links: List<Link>) {
    LinkBuilder.on(this)
        .addLinks(links)
        .build()
}

fun TextView.applyLinkedText(text: CharSequence) {
    this.text = text
    this.movementMethod = TouchableMovementMethod.instance
}

fun CharSequence.createLinks(context: Context, vararg links: Link) {
    val builder = LinkBuilder.from(context, this.toString())
    links.forEach { builder.addLink(it) }
    builder.build()
}

fun TextView.addHeightText(keyword: String, color: Int, underlined: Boolean = false) {
    val builder = LinkBuilder.on(this)
    builder.addLink(Link(keyword).setTextColor(color).setUnderlined(underlined))
    builder.build()
}