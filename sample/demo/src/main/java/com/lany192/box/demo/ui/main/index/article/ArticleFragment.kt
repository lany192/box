package com.lany192.box.demo.ui.main.index.article

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.items.ItemsFragment
import com.github.lany192.log.LogUtils
import com.lany192.box.demo.binder.ArticleBinder
import com.lany192.box.demo.databinding.FragmentArticleBinding
import com.lany192.box.demo.ui.goods.DemoEvent
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
@Route(path = "/page/article")
class ArticleFragment : ItemsFragment<ArticleViewModel, FragmentArticleBinding>() {

    init {
        register(ArticleBinder())
    }

    override fun createRefreshLayout(): SmartRefreshLayout {
        return binding.refreshLayout
    }

    override fun createRecyclerView(): RecyclerView {
        return binding.recyclerView
    }

    override fun init() {
        super.init()
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView.layoutManager as LinearLayoutManager?
                val firstVisibleItemPosition = manager!!.findFirstVisibleItemPosition()
                LogUtils.i("子类滑动测试：$firstVisibleItemPosition")
                binding.demoLayout.setSubclass(firstVisibleItemPosition >= 0)
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: DemoEvent) {
        LogUtils.i("父类滑动测试：" + event.firstVisibleItemPosition)
        binding.demoLayout.setParentClass(event.firstVisibleItemPosition == 19)
    }
}