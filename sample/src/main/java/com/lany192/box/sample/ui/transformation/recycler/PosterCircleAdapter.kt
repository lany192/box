package com.lany192.box.sample.ui.transformation.recycler

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lany192.box.sample.databinding.ItemPosterCircleBinding
import com.lany192.box.sample.ui.transformation.TransformationDetailActivity

class PosterCircleAdapter : RecyclerView.Adapter<PosterCircleAdapter.PosterViewHolder>() {

  private val items = mutableListOf<Poster>()
  private var previousTime = SystemClock.elapsedRealtime()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
    val binding = ItemPosterCircleBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false,
    )
    return PosterViewHolder(binding)
  }

  override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
    val item = items[position]
    holder.binding.run {
      Glide.with(root.context)
        .load(item.poster)
        .into(itemPosterPost)
      itemPosterTitle.text = item.name
      itemPosterRunningTime.text = item.playtime
      root.setOnClickListener {
        val now = SystemClock.elapsedRealtime()
        if (now - previousTime >= itemPosterCircleTransformationLayout.duration) {
          TransformationDetailActivity.startActivity(root.context, itemPosterCircleTransformationLayout, item)
        }
        previousTime = now
      }
    }
  }

  fun addPosterList(list: List<Poster>) {
    items.clear()
    items.addAll(list)
    notifyDataSetChanged()
  }

  override fun getItemCount() = items.size

  class PosterViewHolder(val binding: ItemPosterCircleBinding) :
    RecyclerView.ViewHolder(binding.root)
}
