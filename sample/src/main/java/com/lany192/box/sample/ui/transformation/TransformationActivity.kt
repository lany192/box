package com.lany192.box.sample.ui.transformation


import android.os.Bundle
import android.view.View
import com.github.lany192.arch.activity.VBActivity
import com.github.lany192.arch.databinding.ToolbarDefaultBinding
import com.lany192.box.sample.databinding.ActivityTransformationBinding
import com.lany192.box.sample.ui.transformation.recycler.PosterAdapter
import com.lany192.box.sample.ui.transformation.recycler.PosterMenuAdapter

class TransformationActivity : VBActivity<ActivityTransformationBinding, ToolbarDefaultBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(binding) {
            recyclerView.adapter = PosterAdapter().apply { addPosterList(MockUtil.getMockPosters()) }
            recyclerViewMenu.adapter = PosterMenuAdapter().apply { addPosterList(MockUtil.getMockPosters()) }

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
