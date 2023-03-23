package com.github.lany192.link

import android.os.Handler
import android.text.style.ClickableSpan
import android.view.View

abstract class TouchableBaseSpan : ClickableSpan() {

    var isTouched = false

    /**
     * This TouchableSpan has been clicked.
     * @param widget TextView containing the touchable span
     */
    override fun onClick(widget: View) {
        Handler().postDelayed({ TouchableMovementMethod.touched = false }, 500)
    }

    /**
     * This TouchableSpan has been long clicked.
     * @param widget TextView containing the touchable span
     */
    open fun onLongClick(widget: View) {
        Handler().postDelayed({ TouchableMovementMethod.touched = false }, 500)
    }
}
