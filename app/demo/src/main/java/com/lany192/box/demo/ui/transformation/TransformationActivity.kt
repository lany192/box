package com.lany192.box.demo.ui.transformation


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewBindingActivity
import com.lany192.box.demo.databinding.ActivityTransformationBinding
import com.lany192.box.demo.ui.transformation.recycler.PosterAdapter
import com.lany192.box.demo.ui.transformation.recycler.PosterMenuAdapter
import dagger.hilt.android.AndroidEntryPoint

@Route(path = "/ui/transformation")
@AndroidEntryPoint
class TransformationActivity : ViewBindingActivity<ActivityTransformationBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            recyclerView.adapter =
                PosterAdapter().apply { addPosterList(MockUtil.getMockPosters()) }
            recyclerViewMenu.adapter =
                PosterMenuAdapter().apply { addPosterList(MockUtil.getMockPosters()) }

            fab.setOnClickListener {
                if (!transformationLayout.isTransforming) {
                    backgroundView.visibility = View.VISIBLE
                }
                transformationLayout.startTransform()
            }

            menuHome.setOnClickListener {
                if (!transformationLayout.isTransforming) {
                    backgroundView.visibility = View.GONE
                }
                transformationLayout.finishTransform()
            }
        }
    }
}
