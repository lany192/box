package com.lany192.box.sample.ui.transformation


import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.github.lany192.arch.activity.VBActivity
import com.github.lany192.arch.databinding.ToolbarDefaultBinding
import com.lany192.box.sample.databinding.ActivityTransformDetailBinding
import com.lany192.box.sample.ui.transformation.recycler.Poster
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationEndContainer

class TransformationDetailActivity : VBActivity<ActivityTransformDetailBinding, ToolbarDefaultBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationEndContainer(intent.getParcelableExtra("com.skydoves.transformationlayout"))
        super.onCreate(savedInstanceState)
        intent.getParcelableExtra<Poster>(posterExtraName)?.let {
            Glide.with(this)
                .load(it.poster)
                .into(binding.profileDetailBackground)
            binding.detailTitle.text = it.name
            binding.detailDescription.text = it.description
        }
    }

    companion object {
        const val posterExtraName = "posterExtraName"
        fun startActivity(
            context: Context,
            transformationLayout: TransformationLayout,
            poster: Poster,
        ) {
            val intent = Intent(context, TransformationDetailActivity::class.java)
            intent.putExtra(posterExtraName, poster)
            TransformationCompat.startActivity(transformationLayout, intent)
        }
    }
}
