package com.github.lany192.box.sample.ui.main.index.girl

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.BindingFragment
import com.github.lany192.arch.items.ListAdapter
import com.github.lany192.box.sample.data.binder.GirlBinder
import com.github.lany192.box.sample.databinding.FragmentGirlBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/fragment/girl")
class GirlFragment : BindingFragment<FragmentGirlBinding>() {

    override fun initView() {
        val viewModel = getFragmentViewModel(GirlViewModel::class.java)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val first = IntArray(layoutManager.spanCount)
                layoutManager.findFirstCompletelyVisibleItemPositions(first)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                    //防止第一行到顶部有空白区域
                    layoutManager.invalidateSpanAssignments()
                }
            }
        })
        val adapter = ListAdapter()
        adapter.addItemBinder(String::class.java, GirlBinder())
        binding.recyclerView.adapter = adapter
        viewModel.items.observe(this, { list: List<String> -> adapter.setList(list) })
    }
}