package com.github.lany192.box.widget

import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.lany192.box.R

class FooterView : BaseLoadMoreView() {

    override fun getLoadComplete(holder: BaseViewHolder): View {
        return holder.getView(R.id.view_footer_end_view)
    }

    override fun getLoadEndView(holder: BaseViewHolder): View {
        return holder.getView(R.id.view_footer_end_view)
    }

    override fun getLoadFailView(holder: BaseViewHolder): View {
        return holder.getView(R.id.view_footer_fail_view)
    }

    override fun getLoadingView(holder: BaseViewHolder): View {
        return holder.getView(R.id.view_footer_loading_view)
    }

    override fun getRootView(parent: ViewGroup): View {
        return View.inflate(parent.context, R.layout.view_footer, null)
    }
}